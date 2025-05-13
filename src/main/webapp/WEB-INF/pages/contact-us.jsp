<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/contactUs.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Contact Us - Consultic Style</title>

</head>
<body>
<jsp:include page="header.jsp"/>
<header>
  <p class="header-descrip">CONTACT US</p>
 
</header>

<section>
  <div class="contact-info1">
    <div class="info-box1">
      <h4>WORKING HOURS</h4>
      <p>Mon – Fri: 9 AM – 6 PM<br>Sat – Sun: Closed</p>
    </div>
    <div class="info-box2">
      <h4>FOLLOW US</h4>
      <p>@ArtisanRoots<br>Instagram · Twitter · LinkedIn</p>
    </div>
    <div class="info-box3">
      <h4>CUSTOMER SUPPORT</h4>
      <p>24/7 Assistance Available<br>+4030800</p>
    </div>
  </div>
</section>

<section class="form-section">
  <div class="contact-info">
    <h3>GET IN TOUCH</h3>
    <p>For inquiries or assistance, please feel free to contact our support team.</p>
    
    <div class="info-item">
      <img src="${pageContext.request.contextPath}/resource/images/system/mail.png" width="25px" height="20px">
      <div>
        <strong>Email</strong><br />
        artisanroots@gmail.com
      </div>
    </div>

    <div class="info-item">
      <img src="${pageContext.request.contextPath}/resource/images/system/location.png" width="25px" height="33px">
      <div>
        <strong>Location</strong><br />
        Kathmandu, Nepal
      </div>
    </div>

    <div class="info-item">
      <img src="${pageContext.request.contextPath}/resource/images/system/phone.png" width="25px" height="20px">
      <div>
        <strong>Phone no.</strong><br />
        (+977) 981234567, +977 982345678
      </div>
    </div>

    <h4>Our location</h4>
    <iframe 
      src="https://www.google.com/maps?q=Kathmandu&output=embed" 
      width="100%" 
      height="200">
    </iframe>
  </div>

  <form class="contact-form">
    <p>Send us a Message</p>
    <div class="form-row">
      <label>First Name</label>
      <input type="text" placeholder="First Name">
    </div>
    <div class="form-row">
      <label>Last Name</label>
      <input type="text" placeholder="Last Name">
    </div>
    <div class="form-row">
      <label>Email</label>
      <input type="email" placeholder="Email Address">
    </div>
    <div class="form-row">
      <label>Phone Number</label>
      <input type="text" placeholder="Phone Number">
    </div>
    <div class="form-row">
      <label>Feedback</label>
      <textarea placeholder="Send Us Your Feedback"></textarea>
    </div>
    <div class="form-row">
      <button type="submit" class="submit-button">Submit</button>
    </div>
  </form>
</section>

<jsp:include page="footer.jsp" />
</body>
</html>
