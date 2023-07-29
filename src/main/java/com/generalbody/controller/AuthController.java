package com.generalbody.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.generalbody.dto.UserDto;
import com.generalbody.entity.User;
import com.generalbody.entity.ZoneList;
import com.generalbody.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

/**
 * @author narendra kusam
 */

@Controller
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@Value("${rzp_key_id}")
	private String keyId;

	@Value("${rzp_key_secret}")
	private String secretId;

	@Value("${rzp_year_term_amount}")
	private String yearTermAmount;

	@Value("${rzp_life_term_amount}")
	private String lifeTermAmount;

	@Value("${rzp_currency}")
	private String currencyType;

	@Value("${rzp_company_name}")
	private String companyName;

	@GetMapping("index")
	public String home() {
		return "index";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		List<ZoneList> zoneList = userService.getZoneList();
		model.addAttribute("zoneList", zoneList);
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
			return "redirect:/payment?mail="+user.getEmail();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	@RequestMapping(value = { "/payment" }, method = RequestMethod.GET)
	public String payment(Model model,@RequestParam("mail") String mailId) {

		User user = userService.findByEmail(false, mailId);
		model.addAttribute("keyId", keyId);
		model.addAttribute("currencyType", currencyType);
		model.addAttribute("yearTermCurrency", yearTermAmount);
		model.addAttribute("lifeTermCurrency", lifeTermAmount);
		model.addAttribute("companyName", companyName);
		model.addAttribute("mail", user.getEmail());
		model.addAttribute("contact", user.getMobile());
		model.addAttribute("name", user.getName());
		return "payment";
	}

	@GetMapping("/createOrder")
	@ResponseBody
	public Map<String, Object> createPaymentOrderId(@RequestParam("amount") String amount) {
		Map<String, Object> map = new HashMap<>();
		try {
			RazorpayClient razorpay = new RazorpayClient(keyId, secretId);
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount); 
			orderRequest.put("currency", currencyType);
			orderRequest.put("receipt", "order_rcptid_11");

			Order order = razorpay.orders.create(orderRequest);
			map.put("razorpayOrderId", order.get("id"));
			map.put("status", HttpStatus.OK);
			map.put("statusCode", 200);
			map.put("statusMsg", "successfully created");

		} catch (RazorpayException e) {
			map.put("razorpayOrderId", null);
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
			map.put("statusCode", 500);
			map.put("statusMsg", e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return map;
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

	@RequestMapping(value = {"/paymentSuccess/{amount}/{mail}"}, method = RequestMethod.POST)
	public String paymentSuccess(Model model,
			Authentication authentication,
			/*
			 * @RequestParam("razorpay_payment_id") String razorpayPaymentId,
			 * 
			 * @RequestParam("razorpay_order_id") String razorpayOrderId,
			 * 
			 * @RequestParam("razorpay_signature") String razorpaySignature,
			 */
			@PathVariable Float amount,
			@PathVariable String mail,
			RedirectAttributes redirectAttributes){

		System.out.println(amount+ "***"+ mail+ "***"+ companyName +"***"+" Save all data, which on success we get!");
		User user = userService.findByEmail(false, mail);
		int updateId = userService.activateUserStatus(true, user.getId());
		model.addAttribute("amount", amount);
		model.addAttribute("name", user.getName());
		model.addAttribute("appointmentId", user.getAppointmentId());
		if(updateId>0) {
			try {
				userService.sendMailAfterPayment(user);
				model.addAttribute("mailStatus", true);
				model.addAttribute("mailStatusMsg", "Appointment Id Sent to Your Register Mail, Thank You!!");
			} catch (MessagingException e) {
				e.printStackTrace();
				model.addAttribute("mailStatus", false);
				model.addAttribute("mailStatusErrMsg", e.getMessage());
			}
		}
		return "paid";
	}

}
