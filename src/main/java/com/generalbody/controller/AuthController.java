package com.generalbody.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.generalbody.dto.RazorPay;
import com.generalbody.dto.Response;
import com.generalbody.dto.UserDto;
import com.generalbody.entity.User;
import com.generalbody.entity.ZoneList;
import com.generalbody.service.UserService;
import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

/**
 * @author narendra kusam
 */

@Controller
public class AuthController {

	private UserService userService;
	private RazorpayClient client;

	private static Gson gson = new Gson();

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@Value("${rzp_key_id}")
	private String keyId;

	@Value("${rzp_key_secret}")
	private String secretId;

	@GetMapping("index")
	public String home(){
		return "index";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	// handler method to handle user registration request
	@GetMapping("register")
	public String showRegistrationForm(Model model){
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		List<ZoneList> zoneList = userService.getZoneList();
		model.addAttribute("zoneList", zoneList);
		return "register";
	}

	// handler method to handle register user form submit request
	@PostMapping("/register/save")
	public String registration(@Valid @ModelAttribute("user") UserDto user,
			BindingResult result,
			Model model){
		User existing = userService.findByEmail(true, user.getEmail());
		String paymentPageUrl = "";
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "register";
		}


		try {
			userService.saveUser(user);
			Order order = createRazorPayOrder("1000");
			RazorPay razorPay = getRazorPay((String)order.get("id"), user);
			paymentPageUrl = getPaymentPageUrl((String)order.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(paymentPageUrl)) {
			paymentPageUrl = "users";
		}
		return "redirect:/"+paymentPageUrl;
	}

	public String getPaymentPageUrl(String orderId) {
		return "https://api.razorpay.com/v1/checkout/embedded/" + orderId;
	}
	
	@GetMapping("/users")
	public String listRegisteredUsers(Model model){
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}

	@RequestMapping(value="/createPayment", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createOrder(@RequestBody UserDto user) {
		try {
			/**
			 * creating an order in RazorPay.
			 * new order will have order id. you can get this order id by calling  order.get("id")
			 */
			Order order = createRazorPayOrder( user.getAmount() );
			RazorPay razorPay = getRazorPay((String)order.get("id"), user);

			return new ResponseEntity<String>(gson.toJson(getResponse(razorPay, 200)),
					HttpStatus.OK);
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(gson.toJson(getResponse(new RazorPay(), 500)),
				HttpStatus.EXPECTATION_FAILED);
	}

	private Response getResponse(RazorPay razorPay, int statusCode) {
		Response response = new Response();
		response.setStatusCode(statusCode);
		response.setRazorPay(razorPay);
		return response;
	}	

	private RazorPay getRazorPay(String orderId, UserDto user) {
		RazorPay razorPay = new RazorPay();
		razorPay.setApplicationFee(convertRupeeToPaise("1000"));
		razorPay.setCustomerName(user.getFirstName() +" "+ user.getLastName());
		razorPay.setCustomerEmail(user.getEmail());
		razorPay.setMerchantName("Test");
		razorPay.setPurchaseDescription("TEST PURCHASES");
		razorPay.setRazorpayOrderId(orderId);
		razorPay.setSecretKey(secretId);
		razorPay.setImageURL("/logo");
		razorPay.setTheme("#F37254");
		razorPay.setNotes("notes"+orderId);
		return razorPay;
	}

	private Order createRazorPayOrder(String amount) throws RazorpayException {

		RazorpayClient razorpayClient = new RazorpayClient(keyId, secretId);
		JSONObject options = new JSONObject();
		options.put("amount", convertRupeeToPaise(amount));
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture. 
		return razorpayClient.orders.create(options);
	}

	private String convertRupeeToPaise(String paise) {
		BigDecimal b = new BigDecimal(paise);
		BigDecimal value = b.multiply(new BigDecimal("100"));
		return value.setScale(0, RoundingMode.UP).toString();

	}

}
