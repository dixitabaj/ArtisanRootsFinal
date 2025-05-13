<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<!-- Add this in your HTML head -->

  <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/home.css" />
  <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
<style>

  </style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:include page="header.jsp" />
<section class="main-container">
  <div class="carousel-container">
    <div class="carousel">
        <div class="carousel-images">
          <div class="carousel-images">
            <div style="position: relative;">
              <img src="${pageContext.request.contextPath}/resource/images/system/slider1.png" alt="Banner 1" width="1425px" height="800px">
              <div class="carousel-caption">
                <h2>Shop Our Handmade Collection</h2>
                <p>Unique crafts made with love.</p>
                <a href="#recommended" class="shop-now">Shop Now</a>
              </div>
              </div>
            </div>
            
              <div class="carousel-images">
                <div style="position: relative;">
            <img src="${pageContext.request.contextPath}/resource/images/system/slider3.png" alt="Banner 3"  width="1400px" height="800px">
            <div class="carousel-caption">
              <div class="slider-descrip1">
              <h2>Support Local Creators</h2>
            <p>Join the movement and empower small businesses with every purchase.</p>
            <a href="/ArtisanRoots3/aboutus" class="shop-now">About Us</a>
            </div>
          </div>
              </div>
              </div>
        </div>
    </div>
</div>
</section>
   <!-- <div class="second-offer-container">
    <div class="left-offer">
        <img src="image copy 3.png" width="600px"  height="350px"></div>
        <div class="right-offer">
            <img src="plate.png" width="600px"  height="350px"></div>
</div> -->
   

<section class="feature">
    <div class="first-section">
        <div class="feature-topic">
          <div class="first-row">
            <img src="${pageContext.request.contextPath}/resource/images/system/whyus1.png"width="50px" style="margin-left: 390px;">
            <div class="col">
          <p>UNIQUE</p>
        </div>
      </div>
        </div>
        <div class="description">
          <p>Every piece is one-of-a-kind, made to stand out. No two items are ever the same—your purchase is as distinctive as you are, with its own story and personality.</p>
    
        </div>
        <div class="feature-topic">
          <div class="second-row">
            <img src="${pageContext.request.contextPath}/resource/images/system/whyus2.png"width="50px" style="margin-left: 390px;">
            <div class="col">
              <p>TRADITIONAL</p>
              </div>
        </div>
        </div>
        <div class="description">
 <p>Crafted with age-old techniques passed down through generations. Each item preserves cultural heritage, celebrating the roots and rich history behind every creation.</p>
    
        </div>
    </div>
    <div class="second-section">
        <img src="${pageContext.request.contextPath}/resource/images/system/feature.png" width="350px" height="500px">
    </div>
    <div class="third-section">
      <div class="feature-topic">
        <div class="first-row">
          <img src="${pageContext.request.contextPath}/resource/images/system/whyus4.png"width="50px">
        <p>100% HANDMADE </p>
      </div>
      </div>
      <div class="description1">
        <p>Lovingly handcrafted, not machine-made. Each piece reflects the artisan’s heart and skill, offering a personal touch that no factory can replicate.</p>
    
      </div>
      <div class="feature-topic">
        <div class="second-row">
          <img src="${pageContext.request.contextPath}/resource/images/system/whyus3.png"width="50px">
        <p>SUPPORT LOCAL ARTISANS</p>
      </div>
      </div>
      <div class="description1">
        <p>Empowering local artisans with every purchase you make. You're not just buying a product—you’re investing in communities, families, and dreams.</p>
    
      </div>
    </div>
</section>


   



  
<section class="recommended-for-you">
 <div class="header1">
    <h2>RECOMMENDED</h2>
    <h2>FOR YOU</h2>
  </div>
  <hr class="horizontal-line">
<div class="card-container">
    
        <div class="card">
          <img src="${pageContext.request.contextPath}/resource/images/system/image copy 4.png" alt="Product 1" />
          <div class="card-info">
            <p class="product-name">INDIGO SCENT</p>
            <p class="product-price">$59.99</p>
            <p class="product-meta">⭐⭐⭐⭐☆ (4.5) · 1.2k sold</p>
            <button class="add-cart-btn">Add to Cart</button>
          </div>
        </div>
    
        <div class="card">
          <img src="${pageContext.request.contextPath}/resource/images/system/mostP.png" alt="Nordic Vase">
          <div class="card-info">
            <p class="product-name">NORDIC VASE</p>
            <p class="product-price">Rs 12,900</p>
            <p class="product-meta">⭐⭐⭐⭐ (4.2) · 3.5k sold</p>
            <button class="add-cart-btn">Add to Cart</button>
          </div>
        </div>
        <div class="card">
          <img src="${pageContext.request.contextPath}/resource/images/system/mostP2.png" alt="Product 1" />
          <div class="card-info">
            <p class="product-name">SAND AMPHOR</p>
            <p class="product-price">Rs 8,999</p>
            <p class="product-meta">⭐⭐⭐⭐☆ (4.5) · 1.2k sold</p>
            <button class="add-cart-btn">Add to Cart</button>
          </div>
        </div>
        <div class="card">
          <img src="${pageContext.request.contextPath}/resource/images/system/mostP1.png" alt="Product 1" />
          <div class="card-info">
            <p class="product-name">AQUA POT</p>
            <p class="product-price">Rs. 6,999</p>
            <p class="product-meta">⭐⭐⭐⭐☆ (4.5) · 1.2k sold</p>
            <button class="add-cart-btn">Add to Cart</button>
          </div>
        </div>
        <div class="card">
          <img src="${pageContext.request.contextPath}/resource/images/system/mostP3.png" alt="Product 1" />
          <div class="card-info">
            <p class="product-name">CERAMIC URN</p>
            <p class="product-price">Rs. 5,999</p>
            <p class="product-meta">⭐⭐⭐⭐☆ (4.5) · 1.2k sold</p>
            <button class="add-cart-btn">Add to Cart</button>
          </div>
        </div>
    
        <!-- Add more cards as needed -->
    
      </div>
  

   
</section>



<section class="about-preview">
    <div class="about-content">
      <h2>About ArtisanRoots</h2>
      <p>We connect local Nepali artisans with the world. Every item you see here is handmade with love, rooted in heritage, and crafted with care.</p>
      <a href="/ArtisanRoots3/contactus" class="about-button">Learn More</a>
    </div>
  </section>
  

<jsp:include page="footer.jsp" />
</body>
</html>