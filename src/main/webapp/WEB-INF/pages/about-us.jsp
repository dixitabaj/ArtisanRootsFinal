<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>About Us - ArtisianRoots</title>
  <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500&family=Open+Sans&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/aboutUs.css">
</head>
<body>

<jsp:include page="header.jsp"/>

  <section class="main-section">
    <img src="${pageContext.request.contextPath}/resource/images/system/aboutUs1.png"  alt="About Us Banner" />
    <p>ABOUT US</p>
  </section>

  <section class="intro">
    <!-- <div class="intro-left">
      <img src="aboutUsIntro1.png" width="300px" alt="Intro Image Left" />
    </div> -->
    <!-- <div class="intro-mid"> -->
      <h2>INTRO</h2>
      <p>
        Our journey started with a simple vision:
        To connect passionate local artisans with people who appreciate the beauty of handmade crafts.
        
        In a fast-paced, factory-made world, we wanted to create something different — a space where every product has a soul, a story, and a purpose. Behind each item on our platform is an artist, a tradition, and hours of love and skill poured into every detail.
        
        We built this website to empower artisans, especially those in remote or underserved communities, by giving them a stage to showcase their talent and reach a wider audience.
        Whether it’s handcrafted jewelry, textiles, pottery, or home decor — each piece reflects cultural heritage and artistic dedication.
        
       
        
      </p>
    <!-- </div> -->
    <!-- <div class="intro-right">
      <img src="aboutUsIntro2.png" width="300px" alt="Intro Image Right" />
    </div> -->
  </section>
  <section class="our-gallery">
    <div>
      <h2>OUR GALLERY</h2>

    </div>
    
    <div class="underline"></div>
      <img src="${pageContext.request.contextPath}/resource/images/system/gallery.png" width="1400px">
   
  </section>

  <section class="team">
    <h2>MEET OUR TEAM</h2>
    <div class="underline"></div>

    <div class="team-container">
      <div class="team-member">
        <img src="${pageContext.request.contextPath}/resource/images/system/profile1.png" alt="Team Member" />
        <h4>Shyam Poudel</h4>
        <div class="role">
        <p>Lead Developer</p>
        </div>
        <div class="bio">
        <p>Builds seamless digital experiences for artisans and buyers alike.</p></div>
        <div class="social-icons">
          <a href="#"><i class="fab fa-dribbble"></i></a>
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-google-plus-g"></i></a>
        </div>
      </div>

      <div class="team-member">
        <img src="${pageContext.request.contextPath}/resource/images/system/profile2.png" alt="Team Member" />
        <h4>Ram Shah</h4>
        <div class="role">
          <p>Co-Founder & Designer</p>
        </div>
        <div class="bio">
          <p>Passionate about traditional art and storytelling through design.</p>
        </div>
        <div class="social-icons">
          <a href="#"><i class="fab fa-dribbble"></i></a>
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-google-plus-g"></i></a>
        </div>
      </div>

      <div class="team-member">
        <img src="${pageContext.request.contextPath}/resource/images/system/profile3.png" alt="Team Member" />
        <h4>Ramesha Shrestha</h4>
        <div class="role">
          <p>Marketing Manager</p>
        </div>
        <div class="bio">
          <p>Connects people to art and artists through stories, socials, and heart.</p>
        </div>
        <div class="social-icons">
          <a href="#"><i class="fab fa-dribbble"></i></a>
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-google-plus-g"></i></a>
        </div>
      </div>

      <div class="team-member">
        <img src="${pageContext.request.contextPath}/resource/images/system/profile4.png" alt="Team Member" />
        <h4>Sita Gautam</h4>
        <div class="role">
          <p>Product Curator</p>
        </div>
        <div class="bio">
          <p>Selects handcrafted pieces that reflect culture, care, and creativity.</p>
        </div>
        <div class="social-icons">
          <a href="#"><i class="fab fa-dribbble"></i></a>
          <a href="#"><i class="fab fa-facebook-f"></i></a>
          <a href="#"><i class="fab fa-google-plus-g"></i></a>
        </div>
      </div>
    </div>
  </section>
  <section>
    <div class="customer-services">
      <div class="content">
        <h2>FREQUENTLY ASKED QUESTIONS</h2>
    
        <div class="accordion">
          <div class="accordion-item">
            <input type="checkbox" id="faq-1" checked>
            <label for="faq-1" class="accordion-title">How do I purchase products?</label>
            <div class="accordion-content">
              <p>Simply browse through our catalog, select the item you'd like to purchase, and proceed to checkout. We offer multiple payment options to suit your preferences.</p>
            </div>
          </div>
    
          <div class="accordion-item">
            <input type="checkbox" id="faq-2">
            <label for="faq-2" class="accordion-title">How can I become an artisan on your platform?</label>
            <div class="accordion-content">
              <p>To join as an artisan, simply sign up on our website, create a profile, and submit your products for approval. Once approved, you can start selling your handmade creations.</p>
            </div>
          </div>
    
          <div class="accordion-item">
            <input type="checkbox" id="faq-3">
            <label for="faq-3" class="accordion-title">What shipping methods do you offer?</label>
            <div class="accordion-content">
              <p>We offer worldwide shipping through reliable courier services. Shipping costs and times vary depending on your location, and you'll be notified of shipping details when you place an order.</p>
            </div>
          </div>
    
          <div class="accordion-item">
            <input type="checkbox" id="faq-4">
            <label for="faq-4" class="accordion-title">Can I return a product?</label>
            <div class="accordion-content">
              <p>We want you to be happy with your purchase! If you're not satisfied, you can request a return within 14 days of receiving your product. Please review our return policy for full details.</p>
            </div>
          </div>
          <div class="accordion-item">
            <input type="checkbox" id="faq-5">
            <label for="faq-5" class="accordion-title">How can I contact customer support?</label>
            <div class="accordion-content">
              <p>If you have any questions or need assistance, you can reach our customer support team via email, phone, or our live chat option available on the website.</p>
            </div>
          </div>
  
          <div class="accordion-item">
            <input type="checkbox" id="faq-6">
            <label for="faq-6" class="accordion-title">Do you offer gift wrapping for products?</label>
            <div class="accordion-content">
              <p>Yes! We offer gift wrapping services for selected items. You can choose this option during checkout.</p>
            </div>
          </div>
  
          <div class="accordion-item">
            <input type="checkbox" id="faq-7">
            <label for="faq-7" class="accordion-title">Can I request custom-made items?</label>
            <div class="accordion-content">
              <p>Yes, many of our artisans offer custom-made products. You can contact the artisan directly through their profile to discuss your custom order details.</p>
            </div>
          </div>
        </div>
      </div>
  
      <div class="image">
        <img src="${pageContext.request.contextPath}/resource/images/system/vase.png" alt="Handcrafted Vase">
      </div>
    </div>
  </section>
  
<jsp:include page="footer.jsp" />
</body>
</html>
