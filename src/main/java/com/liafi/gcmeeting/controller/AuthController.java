package com.liafi.gcmeeting.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.liafi.gcmeeting.dto.TransactionDto;
import com.liafi.gcmeeting.dto.UserDto;
import com.liafi.gcmeeting.entity.ClubTypeList;
import com.liafi.gcmeeting.entity.Relative;
import com.liafi.gcmeeting.entity.Role;
import com.liafi.gcmeeting.entity.User;
import com.liafi.gcmeeting.entity.ZoneList;
import com.liafi.gcmeeting.service.UserService;

/**
 * @author narendra kusam
 */

@Controller
@RequestMapping("/gcmeeting")
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * @Value("${rzp_key_id}") private String keyId;
	 * 
	 * @Value("${rzp_key_secret}") private String secretId;
	 * 
	 * @Value("${rzp_reg_fee}") private String regFee;
	 * 
	 * @Value("${rzp_currency}") private String currencyType;
	 * 
	 * @Value("${rzp_company_name}") private String companyName;
	 */

	@Value("${rzp_reg_fee}")
	private String regFee;

	@GetMapping("/index")
	public String home() {
		return "index";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/logout")
	public String logoutForm() {
		return "login";
	}

	@GetMapping("/contact")
	public String contact() {
		return "contactUs";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		List<Relative> tempList = new ArrayList<Relative>();
		Relative tempRelative = new Relative();
		tempList.add(tempRelative);
		user.setRelatives(tempList);
		model.addAttribute("user", user);
		List<ZoneList> zoneList = userService.getZoneList();
		List<ClubTypeList> clubTypeList = userService.getClubTypeList();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("clubTypeList", clubTypeList);
		return "register";
	}

	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {

		User existing = userService.findByEmail(true, user.getEmail());
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}

		try {
			userService.saveUser(user);
			return "redirect:/gcmeeting/payment?mail="+user.getEmail();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@RequestMapping(value = { "/payment" }, method = RequestMethod.GET)
	public String payment(Model model,@RequestParam("mail") String mailId) {

		User user = userService.findByEmail(false, mailId);
		/*
		 * model.addAttribute("keyId", keyId); model.addAttribute("currencyType",
		 * currencyType); 
		 * model.addAttribute("companyName", companyName);
		 */
		model.addAttribute("regFeeAmount", regFee);
		model.addAttribute("mail", user.getEmail());
		model.addAttribute("contact", user.getMobile());
		model.addAttribute("name", user.getName());
		return "payment";
	}

	/*
	 * @GetMapping("/createOrder")
	 * 
	 * @ResponseBody public Map<String, Object>
	 * createPaymentOrderId(@RequestParam("amount") String amount) { Map<String,
	 * Object> map = new HashMap<>(); try { RazorpayClient razorpay = new
	 * RazorpayClient(keyId, secretId); JSONObject orderRequest = new JSONObject();
	 * orderRequest.put("amount", amount); orderRequest.put("currency",
	 * currencyType); orderRequest.put("receipt", "order_rcptid_11");
	 * 
	 * Order order = razorpay.orders.create(orderRequest);
	 * map.put("razorpayOrderId", order.get("id")); map.put("status",
	 * HttpStatus.OK); map.put("statusCode", 200); map.put("statusMsg",
	 * "successfully created");
	 * 
	 * } catch (RazorpayException e) { map.put("razorpayOrderId", null);
	 * map.put("status", HttpStatus.INTERNAL_SERVER_ERROR); map.put("statusCode",
	 * 500); map.put("statusMsg", e.getMessage()); e.printStackTrace(); } return
	 * map; }
	 */

	@RequestMapping(value = { "/usersList" }, method = RequestMethod.GET)
	public String usersList(Model model,@RequestParam("username") String mailId) {
		List<UserDto> users = new ArrayList<UserDto>();
		UserDto userDto = new UserDto();
		try {
			User user = userService.findByEmail(true, mailId);
			if(user.getId()>0) {
				List<Role> roleList = user.getRoles();
				if(roleList.get(0).getName().equalsIgnoreCase("ROLE_ADMIN")) {
					users = userService.findAll();
					model.addAttribute("users", users);
					model.addAttribute("admin", mailId);
					return "users";
				}else {
					userDto = userService.convertEntityToDto(user);
					model.addAttribute("user", userDto);
					return "userInfo";
				}
			}else {
				return "redirect:'/gcmeeting/login?error'";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	@GetMapping("/users")
	public String listRegisteredUsers(Model model,@RequestParam(value="status", defaultValue="paid", required = false) String status) {
		List<UserDto> users = new ArrayList<UserDto>();
		try {
			if(status.equalsIgnoreCase("paid")) {
				users = userService.findAllUsers(true);
			}else {
				users = userService.findAllUsers(false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		model.addAttribute("users", users);
		return "users";
	}

	/*
	 * @RequestMapping(value = {"/paymentSuccess/{amount}/{mail}"}, method =
	 * RequestMethod.POST) public String paymentSuccess(Model model, Authentication
	 * authentication,
	 * 
	 * @RequestParam("razorpay_payment_id") String razorpayPaymentId,
	 * 
	 * @RequestParam("razorpay_order_id") String razorpayOrderId,
	 * 
	 * @RequestParam("razorpay_signature") String razorpaySignature,
	 * 
	 * @PathVariable Float amount,
	 * 
	 * @PathVariable String mail, RedirectAttributes redirectAttributes){
	 * 
	 * User user = userService.findByEmail(false, mail); user.setStatus(true);
	 * user.setPaymentId(razorpayPaymentId); user.setOrderId(razorpayOrderId); User
	 * updateUser = userService.updateUserData(user); model.addAttribute("amount",
	 * amount); model.addAttribute("name", user.getName());
	 * model.addAttribute("appointmentId", user.getAppointmentId());
	 * if(updateUser.getId()>0) { try { userService.sendMailAfterPayment(user);
	 * model.addAttribute("mailStatus", true); model.addAttribute("mailStatusMsg",
	 * "Appointment Id Sent to Your Register Mail, Thank You!!"); } catch
	 * (MessagingException e) { e.printStackTrace();
	 * model.addAttribute("mailStatus", false);
	 * model.addAttribute("mailStatusErrMsg", e.getMessage()); } } return "paid"; }
	 */

	@GetMapping("/user/profile/{userId}")
	public String listRegisteredUsers(Model model,@PathVariable long userId) {
		try {
			User userData = userService.findById(userId);
			UserDto user = userService.convertEntityToDto(userData);
			model.addAttribute("user", user);
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return "userInfo";
	}

	@GetMapping("/user/changePaymentStatus/{userId}/{admin}")
	public String changePaymentStatus(Model model,@PathVariable long userId,@PathVariable String admin,HttpServletRequest request) {
		try {
			User userData = userService.findById(userId);
			userData.setStatus(true);
			userData.setPaymentId("Done");
			userData.setOrderId("Done");
			User updateUser = userService.updateUserData(userData);
			if(updateUser.getId()>0) {
				try {
					userService.sendMailAfterPayment(updateUser);
					model.addAttribute("mailStatus", true);
					model.addAttribute("mailStatusMsg", "Appointment Id Sent to Your Register Mail, Thank You!!");
				} catch (MessagingException e) {
					e.printStackTrace();
					model.addAttribute("mailStatus", false);
					model.addAttribute("mailStatusErrMsg", e.getMessage());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null)
				.build()
				.toUriString();
		baseUrl = baseUrl + "/liafi/gcmeeting/usersList?username="+admin;
		return "redirect:"+baseUrl;
	}

	@GetMapping("/usersByStatus")
	public String usersByStatus(Model model,@RequestParam(value="status", required = true) String status) {
		List<UserDto> users = new ArrayList<UserDto>();
		try {
			if(status.equalsIgnoreCase("paid")) {
				users = userService.findAllUsers(true);
			}else {
				users = userService.findAllUsers(false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		model.addAttribute("users", users);
		return "usersByStatus";
	}

	@PostMapping("/trasactionSubmit")
	public String trasactionSubmit(@Valid @ModelAttribute("transaction") TransactionDto trans, BindingResult result, 
			Model model, HttpServletRequest request) {

		try {
			User userData = userService.findByEmail(false,trans.getUserMailId());
			userData.setPaymentId(trans.getTransactionNumber());
			userData.setOrderId("Done");
			User updateUser = userService.updateUserData(userData);
			if(updateUser.getId()>0) {
				try {
					userService.sendMailAfterPaymentToAdmin(updateUser);
					model.addAttribute("mailStatus", true);
					model.addAttribute("mailStatusMsg", "Success");
				} catch (MessagingException e) {
					e.printStackTrace();
					model.addAttribute("mailStatus", false);
					model.addAttribute("mailStatusErrMsg", e.getMessage());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
		String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null)
				.build()
				.toUriString();
		baseUrl = baseUrl + "/liafi/gcmeeting/login?transaction";
		return "redirect:"+baseUrl;
	}
}
