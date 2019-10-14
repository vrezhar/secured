<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 09.10.19
  Time: 19:13
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
</head>

<body>
<div class="body-wrap" data-template-mode="cards">
    <div id="st-container" class="st-container">

        <nav class="st-menu st-effect-1" id="menu-1">
            <div class="st-profile">
                <div class="st-profile-user-wrapper">
                    <div class="profile-user-image">
                        <img src="../../assets/images/prv/people/person-1.jpg" class="img-circle" />
                    </div>
                    <div class="profile-user-info">
                        <span class="profile-user-name">Bertram Ozzie</span>
                        <span class="profile-user-email">username@example.com</span>
                    </div>
                </div>
            </div>

            <div class="st-menu-list mt-2">
                <ul>
                    <li>
                        <a href="#">
                            <i class="ion-ios-bookmarks-outline"></i> Theme documentation
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="ion-ios-cart-outline"></i> Purchase Tribus
                        </a>
                    </li>
                </ul>
            </div>

            <h3 class="st-menu-title">Account</h3>
            <div class="st-menu-list">
                <ul>
                    <li>
                        <a href="#">
                            <i class="ion-ios-person-outline"></i> User profile
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="ion-ios-location-outline"></i> My addresses
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="ion-card"></i> My cards
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="ion-ios-unlocked-outline"></i> Password update
                        </a>
                    </li>
                </ul>
            </div>

            <h3 class="st-menu-title">Support center</h3>
            <div class="st-menu-list">
                <ul>
                    <li>
                        <a href="#">
                            <i class="ion-ios-information-outline"></i> About Tribus
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="ion-ios-email-outline"></i> Contact &amp; support
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-camera"></i> Getting started
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="st-pusher">
            <div class="st-content">
                <div class="st-content-inner">

                    <!-- Header -->
                    <div class="header">
                        <!-- Top Bar -->
                        <div class="top-navbar">
                            <div class="container">
                                <div class="row">
                                    <div class="col-md-6">
                                        <span class="aux-text d-none d-md-inline-block">
                                            <ul class="inline-links inline-links--style-1">
                                                <li class="d-none d-lg-inline-block">
                                                    Boomerang - Multipurpose Bootstrap Theme                        </li>
                                                <li>
                                                    <i class="fa fa-envelope"></i>
                                                    <a href="#">support@webpixels.io</a>
                                                </li>
                                            </ul>
                                        </span>
                                    </div>

                                    <div class="col-md-6">
                                        <nav class="top-navbar-menu">
                                            <ul class="top-menu">
                                                <li>
                                                    <sec:ifLoggedIn>
                                                        <g:link controller="logout">
                                                            Logout
                                                        </g:link>
                                                    </sec:ifLoggedIn>
                                                </li>
                                                <li><a href="#">Create account</a></li>
                                                <li><a href="#" data-toggle="global-search"><i class="fa fa-search"></i></a></li>
                                                <li class="dropdown">
                                                    <a href="#">
                                                        <i class="fa fa-shopping-cart"></i>
                                                    </a>
                                                    <ul class="sub-menu">
                                                        <li>
                                                            <div class="dropdown-cart px-0">
                                                                <div class="dc-header">
                                                                    <h3 class="heading heading-6 strong-600">Cart</h3>
                                                                </div>
                                                                <div class="dc-item">
                                                                    <div class="d-flex align-items-center">
                                                                        <div class="dc-image">
                                                                            <a href="#">
                                                                                <img src="../../assets/images/prv/shop/accessories/img-1a.png" class="img-fluid" alt="">
                                                                            </a>
                                                                        </div>
                                                                        <div class="dc-content">
                                                                            <span class="d-block dc-product-name text-capitalize strong-600 mb-1">
                                                                                <a href="#">
                                                                                    Wood phone case
                                                                                </a>
                                                                            </span>

                                                                            <span class="dc-quantity">x2</span>
                                                                            <span class="dc-price">$50.00</span>
                                                                        </div>
                                                                        <div class="dc-actions">
                                                                            <button>
                                                                                <i class="ion-close"></i>
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="dc-item">
                                                                    <div class="d-flex align-items-center">
                                                                        <div class="dc-image">
                                                                            <a href="#">
                                                                                <img src="../../assets/images/prv/shop/accessories/img-2a.png" class="img-fluid" alt="">
                                                                            </a>
                                                                        </div>
                                                                        <div class="dc-content">
                                                                            <span class="d-block dc-product-name text-capitalize strong-600 mb-1">
                                                                                <a href="#">
                                                                                    Leather bag
                                                                                </a>
                                                                            </span>

                                                                            <span class="dc-quantity">x1</span>
                                                                            <span class="dc-price">$250.00</span>
                                                                        </div>
                                                                        <div class="dc-actions">
                                                                            <button>
                                                                                <i class="ion-close"></i>
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="dc-item">
                                                                    <div class="d-flex align-items-center">
                                                                        <div class="dc-image">
                                                                            <a href="#">
                                                                                <img src="../../assets/images/prv/shop/accessories/img-3a.png" class="img-fluid" alt="">
                                                                            </a>
                                                                        </div>
                                                                        <div class="dc-content">
                                                                            <span class="d-block dc-product-name text-capitalize strong-600 mb-1">
                                                                                <a href="#">
                                                                                    Brown leather wallet
                                                                                </a>
                                                                            </span>

                                                                            <span class="dc-quantity">x1</span>
                                                                            <span class="dc-price">$99.00</span>
                                                                        </div>
                                                                        <div class="dc-actions">
                                                                            <button>
                                                                                <i class="ion-close"></i>
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="dc-item py-4">
                                                                    <span class="subtotal-text">Subtotal</span>
                                                                    <span class="subtotal-amount">$450.00</span>
                                                                </div>
                                                                <div class="py-4 text-center">
                                                                    <ul class="inline-links inline-links--style-3">
                                                                        <li class="pr-3">
                                                                            <a href="#" class="link link--style-2 text-capitalize">
                                                                                <i class="ion-person"></i> My account
                                                                            </a>
                                                                        </li>
                                                                        <li class="pr-3">
                                                                            <a href="#" class="link link--style-1 text-capitalize">
                                                                                <i class="ion-bag"></i> View cart
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a href="#" class="link link--style-1 text-capitalize">
                                                                                <i class="ion-forward"></i> Checkout
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li class="aux-languages dropdown">
                                                    <a href="#">
                                                        <img src="../../assets/images/icons/flags/ro.png">
                                                    </a>
                                                    <ul id="auxLanguages" class="sub-menu">
                                                        <li>
                                                            <a href="#"><span class="language">German</span></a>
                                                        </li>
                                                        <li>
                                                            <span class="language language-active">English</span>
                                                        </li>
                                                        <li>
                                                            <a href="#"><span class="language">Greek</span></a>
                                                        </li>
                                                        <li>
                                                            <a href="#"><span class="language">Spanish</span></a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li><a href="#" class="btn-st-trigger" data-effect="st-effect-1"><i class="fa fa-ellipsis-h"></i></a></li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Global Search -->
                        <section id="sctGlobalSearch" class="global-search global-search-overlay">
                            <div class="container">
                                <div class="global-search-backdrop mask-dark--style-2"></div>

                                <!-- Search form -->
                                <form class="form-horizontal form-global-search z-depth-2-top" role="form">
                                    <div class="px-4">
                                        <div class="row">
                                            <div class="col-12">
                                                <input type="text" class="search-input" placeholder="Type and hit enter ...">
                                            </div>
                                        </div>
                                    </div>
                                    <a href="#" class="close-search" data-toggle="global-search" title="Close search bar"></a>
                                </form>
                            </div>
                        </section>

                        <!-- Navbar -->
                        <nav class="navbar navbar-expand-lg navbar--bold navbar-light bg-default  navbar--bb-1px">
                            <div class="container navbar-container">
                                <!-- Brand/Logo -->
                                <a class="navbar-brand" href="../../index.html">
                                    <img src="../../assets/images/logo/logo-1-b.png" class="" alt="Boomerang">
                                </a>

                                <div class="d-inline-block">
                                    <!-- Navbar toggler  -->
                                    <button class="navbar-toggler hamburger hamburger-js hamburger--spring" type="button" data-toggle="collapse" data-target="#navbar_main" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                                        <span class="hamburger-box">
                                            <span class="hamburger-inner"></span>
                                        </span>
                                    </button>
                                </div>

                                <div class="collapse navbar-collapse align-items-center justify-content-end" id="navbar_main">
                                    <!-- Navbar search - For small resolutions -->
                                    <div class="navbar-search-widget b-xs-bottom py-3 d-lg-none d-none">
                                        <form class="" role="form">
                                            <div class="input-group input-group-lg">
                                                <input type="text" class="form-control" placeholder="Search for...">
                                                <span class="input-group-btn">
                                                    <button class="btn btn-base-3" type="button">Go!</button>
                                                </span>
                                            </div>
                                        </form>
                                    </div>

                                    <!-- Navbar links -->
                                    <ul class="navbar-nav">
                                        <li class="nav-item dropdown megamenu">
                                            <a class="nav-link" href="../../index.html">
                                                Overview
                                            </a>
                                        </li>
                                        <li class="nav-item dropdown">
                                            <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Pages
                                            </a>

                                            <div class="dropdown-menu dropdown-menu-xl py-0 px-0 overflow--hidden" aria-labelledby="navbar_1_dropdown_1">
                                                <div class="list-group rounded">
                                                    <a href="../../pages-home.html" class="list-group-item list-group-item-action d-flex align-items-center">
                                                        <div class="list-group-content">
                                                            <div class="list-group-heading heading heading-6 mb-1">Home pages</div>
                                                            <p class="text-sm mb-0">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                                        </div>
                                                    </a>
                                                    <a href="../../pages-inner.html" class="list-group-item list-group-item-action d-flex align-items-center">
                                                        <div class="list-group-content">
                                                            <div class="list-group-heading heading heading-6 mb-1">Inner pages</div>
                                                            <p class="text-sm mb-0">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                                        </div>
                                                    </a>
                                                    <a href="../../pages-profile.html" class="list-group-item list-group-item-action d-flex align-items-center">
                                                        <div class="list-group-content">
                                                            <div class="list-group-heading heading heading-6 mb-1">Profile pages</div>
                                                            <p class="text-sm mb-0">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                                        </div>
                                                    </a>
                                                    <a href="../../pages-blog.html" class="list-group-item list-group-item-action d-flex align-items-center">
                                                        <div class="list-group-content">
                                                            <div class="list-group-heading heading heading-6 mb-1">Blog pages</div>
                                                            <p class="text-sm mb-0">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                                        </div>
                                                    </a>
                                                    <a href="../../pages-shop.html" class="list-group-item list-group-item-action d-flex align-items-center">
                                                        <div class="list-group-content">
                                                            <div class="list-group-heading heading heading-6 mb-1">Shop pages</div>
                                                            <p class="text-sm mb-0">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                                        </div>
                                                    </a>
                                                    <a href="../../pages-real-estate.html" class="list-group-item list-group-item-action d-flex align-items-center">
                                                        <div class="list-group-content">
                                                            <div class="list-group-heading heading heading-6 mb-1">Real estate pages</div>
                                                            <p class="text-sm mb-0">Lorem ipsum dolor sit amet consectetur adipiscing eiusmod tempor</p>
                                                        </div>
                                                    </a>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="nav-item dropdown megamenu">
                                            <a class="nav-link" href="../../demos.html">
                                                Demos
                                            </a>
                                        </li>
                                        <li class="nav-item dropdown megamenu">
                                            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Components
                                            </a>

                                            <div class="dropdown-menu">
                                                <div class="mega-dropdown-menu row row-no-padding">
                                                    <div class="mega-dropdown-col mega-dropdown-col-cover mega-dropdown-col-cover--left col-lg-5 d-none d-lg-block d-xl-block" style="background-image: url(../../assets/images/prv/other/img-14-1000x900.jpg); background-position: center center;">
                                                        <span class="mask mask-base-1--style-1 alpha-8"></span>
                                                        <div class="mega-dropdown-col-inner d-flex align-items-center no-padding">
                                                            <div class="col">
                                                                <div class="row align-items-center">
                                                                    <div class="col-12">
                                                                        <div class="px-4">
                                                                            <h2 class="heading heading-2 strong-600 c-white">
                                                                                Documentation and Shortcodes
                                                                            </h2>
                                                                            <p class="strong-300 c-white mt-4">
                                                                                Get started fast and easy with Boomerang using the documentation and shortcode examples. No matter
                                                                                you are a developer or new to web design, you will
                                                                                find our theme very easy to customize with
                                                                                an intuitive HTML markup.
                                                                            </p>
                                                                            <div class="btn-container mt-5">
                                                                                <a href="../../documentation/getting-started/introduction.html" target="_blank" class="btn btn-styled btn-white btn-outline btn-circle">Read the Docs</a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4 col-lg-2 ml-lg-auto">
                                                        <ul class="megadropdown-links">
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/alerts.html">
                                                                    Alerts
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/badges.html">
                                                                    Badges
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/buttons.html">
                                                                    Buttons
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/cards.html">
                                                                    Cards
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/collapse.html">
                                                                    Collapse
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/countdown.html">
                                                                    Countdown
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/dropdown.html">
                                                                    Dropdowns
                                                                </a>
                                                            </li>

                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/forms.html">
                                                                    Forms
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/instagram.html">
                                                                    Instagram
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/lightbox.html">
                                                                    Lightbox
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/maps.html">
                                                                    Maps
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/masonry.html">
                                                                    Masonry
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>

                                                    <div class="col-md-4 col-lg-2">
                                                        <ul class="megadropdown-links">
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/pagination.html">
                                                                    Pagination
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/progress.html">
                                                                    Progress bars
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/recaptcha.html">
                                                                    Recaptcha
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/content/titles.html">
                                                                    Section titles
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/forms.html#ex_component_sliders">
                                                                    Sliders
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/tabs.html">
                                                                    Tabs
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/components/typed-animations.html">
                                                                    Typed animations
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/headers.html">
                                                                    Headers
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../html/default/sliders/boomerang-slider/index.html">
                                                                    Boomerang slider
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../html/default/sliders/revolution-slider/index.html">
                                                                    Revolution slider
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/footers.html">
                                                                    Footers
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/hero.html">
                                                                    Hero blocks
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>

                                                    <div class="col-md-4 col-lg-2">
                                                        <ul class="megadropdown-links">

                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/icon.html">
                                                                    Icon blocks
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/jobs.html">
                                                                    Job blocks
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/pricing.html">
                                                                    Pricing blocks
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/product.html">
                                                                    Product blocks
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/real-estate.html">
                                                                    Real-Estate blocks
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/team.html">
                                                                    Team blocks
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/testimonials.html">
                                                                    Testimonial blocks
                                                                </a>
                                                            </li>

                                                            <li>
                                                                <a class="dropdown-item" href="../../documentation/blocks/comments.html">
                                                                    Comment blocks
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>

                                    <ul class="navbar-nav ml-lg-auto">
                                        <li class="nav-item">
                                            <a href="../../documentation/getting-started/introduction.html" class="nav-link">
                                                Docs
                                            </a>
                                        </li>
                                    </ul>
                                </div>


                                <div class="pl-4 d-none d-lg-inline-block">
                                    <a href="https://goo.gl/9BbH7R" class="btn btn-styled btn-sm btn-base-1 btn-circle" target="_blank">
                                        Purchase now
                                    </a>
                                </div>
                            </div>
                        </nav>
                    </div>

                    <section class="slice-sm sct-color-1">
                        <div class="profile">
                            <div class="container">
                                <div class="row cols-xs-space cols-sm-space cols-md-space">
                                    <div class="col-lg-4">
                                        <div class="sidebar sidebar--style-2 no-border">
                                            <div class="widget">
                                                <!-- Profile picture -->
                                                <div class="profile-picture profile-picture--style-2">
                                                    <img src="../../assets/images/prv/people/person-7.jpg" class="img-center">
                                                    <a href="#" class="btn-aux">
                                                        <i class="ion ion-edit"></i>
                                                    </a>
                                                </div>

                                                <!-- Profile details -->
                                                <div class="profile-details">
                                                    <h2 class="heading heading-4 strong-500 profile-name">Elisabeth Alanna</h2>
                                                    <h3 class="heading heading-6 strong-400 profile-occupation mt-3">Founder, Web Developer</h3>
                                                    <h3 class="heading heading-light heading-6 strong-400 profile-location">Bucharest, Romania</h3>
                                                </div>

                                                <!-- Profile connect -->
                                                <div class="profile-connect mt-4">
                                                    <a href="#" class="btn btn-styled btn-block btn-rounded btn-base-1">Follow</a>
                                                    <a href="#" class="btn btn-styled btn-block btn-rounded btn-base-2">Send message</a>
                                                </div>

                                                <!-- Profile stats -->
                                                <div class="profile-stats clearfix">
                                                    <div class="stats-entry">
                                                        <span class="stats-count">180</span>
                                                        <span class="stats-label text-uppercase">Projects</span>
                                                    </div>
                                                    <div class="stats-entry">
                                                        <span class="stats-count">1.3K</span>
                                                        <span class="stats-label text-uppercase">Followers</span>
                                                    </div>
                                                </div>

                                                <!-- Profile connected accounts -->
                                                <div class="profile-useful-links clearfix">
                                                    <div class="useful-links">
                                                        <a href="#" class="link link--style-1">
                                                            <i class="icon ion-social-instagram-outline"></i>
                                                            instagram.com/webpixels_io
                                                        </a>
                                                        <a href="#" class="link link--style-1">
                                                            <i class="icon ion-social-dribbble"></i>
                                                            dribbble.com/webpixels
                                                        </a>

                                                        <a href="#" class="link link--style-1">
                                                            <i class="icon ion-earth"></i>
                                                            webpixels.io
                                                        </a>
                                                    </div>
                                                </div>

                                                <div class="profile-useful-links clearfix">
                                                    <div class="useful-links">
                                                        <a href="#" class="link link--style-1">
                                                            <i class="icon ion-code-download"></i>
                                                            Export page as PDF
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-8">
                                        <div class="main-content">
                                            <!-- Page title -->
                                            <div class="page-title">
                                                <div class="row align-items-center">
                                                    <div class="col-lg-6 col-12">
                                                        <h2 class="heading heading-5 text-capitalize strong-500 mb-0">
                                                            <a href="#" class="link text-underline--none">
                                                                <i class="ion-ios-arrow-back"></i> Settings
                                                            </a>
                                                        </h2>
                                                    </div>
                                                    <div class="col-lg-6 col-12">

                                                    </div>
                                                </div>
                                            </div>

                                            <div class="link-menu link-menu--style-3 py-4 border-bottom">
                                                <a href="../../pages/profile/account-settings.html" class="active">Settings</a>
                                                <a href="../../pages/profile/account-orders.html">Orders</a>
                                                <a href="../../pages/profile/account-cards.html">Cards</a>
                                                <a href="../../pages/profile/account-security.html">Security</a>
                                                <a href="../../pages/profile/account-connections.html">Connections</a>
                                            </div>

                                            <!-- Account settings -->
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <form class="form-default" data-toggle="validator" role="form">
                                                        <!-- General information -->
                                                        <div class="card no-border bg-transparent">
                                                            <div class="card-title px-0 pb-0 no-border">
                                                                <h3 class="heading heading-6 strong-600">
                                                                    General information
                                                                </h3>
                                                                <p class="mt-1 mb-0">
                                                                    You can help us, by filling your data, create you a much better experience using our website.
                                                                </p>
                                                            </div>
                                                            <div class="card-body px-0">
                                                                <div class="row">
                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">First name</label>
                                                                            <input type="text" class="form-control form-control-lg">
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Last name</label>
                                                                            <input type="text" class="form-control form-control-lg">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <hr class="mt-0 mb-0">

                                                        <!-- Account info -->
                                                        <div class="card no-border bg-transparent">
                                                            <div class="card-title px-0 pb-0 no-border">
                                                                <h3 class="heading heading-6 strong-600">
                                                                    Account info
                                                                </h3>
                                                                <p class="mt-1 mb-0">
                                                                    Edit your email and also, your newsletter subscriptions.
                                                                </p>
                                                            </div>
                                                            <div class="card-body px-0">
                                                                <div class="row align-items-center">
                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Email</label>
                                                                            <input type="email" class="form-control form-control-lg" value="name@example.com" disabled>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-6 col-lg-4">
                                                                        <a href="#" class="link link-sm link--style-2 mt-2">Change email</a>
                                                                    </div>
                                                                </div>

                                                                <div class="row">
                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Email frequency</label>
                                                                            <select class="form-control form-control-lg">
                                                                                <option value="">Daily</option>
                                                                                <option value="">Weekly</option>
                                                                                <option value="">Monthly</option>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <hr class="mt-0 mb-0">

                                                        <!-- Change password -->
                                                        <div class="card no-border bg-transparent">
                                                            <div class="card-title px-0 pb-0 no-border">
                                                                <h3 class="heading heading-6 strong-600">
                                                                    Change password
                                                                </h3>
                                                            </div>
                                                            <div class="card-body px-0">
                                                                <div class="row">
                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Current password</label>
                                                                            <input type="text" class="form-control form-control-lg">
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">New password</label>
                                                                            <input type="text" class="form-control form-control-lg">
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Confirm password</label>
                                                                            <input type="text" class="form-control form-control-lg">
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="text-right">
                                                                    <a href="#" class="btn btn-base-1">Change password</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>

                                            <hr class="mt-0 mb-0">

                                            <!-- Addresses -->
                                            <div class="py-4">
                                                <h3 class="heading heading-6 strong-600">My addresses</h3>
                                            </div>

                                            <div class="row cols-md-space cols-sm-space cols-xs-space">
                                                <div class="col-lg-6">
                                                    <div class="card">
                                                        <div class="px-4 py-4">
                                                            <p class="c-gray-light">
                                                                Desiree Jinny
                                                                <br>
                                                                1333 Deerfield, State College
                                                                <br>
                                                                PA, 16803
                                                            </p>

                                                            <p class="c-gray-light mt-3">
                                                                E: desiree@example.com
                                                                <br>
                                                                T: 555 965 452
                                                            </p>

                                                            <span class="clearfix"></span>

                                                            <ul class="inline-links inline-links--style-2">
                                                                <li class="d-none d-lg-inline-block">
                                                                    <a href="#" class="link link--style-2 strong-600">Edit address</a>
                                                                </li>
                                                                <li>
                                                                    <a href="#" class="link link--style-2 strong-600">Add new</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-lg-6">
                                                    <div class="card">
                                                        <div class="px-4 py-4">
                                                            <p class="c-gray-light">
                                                                Desiree Jinny
                                                                <br>
                                                                1333 Deerfield, State College
                                                                <br>
                                                                PA, 16803
                                                            </p>

                                                            <p class="c-gray-light mt-3">
                                                                E: desiree@example.com
                                                                <br>
                                                                T: 555 965 452
                                                            </p>

                                                            <span class="clearfix"></span>

                                                            <ul class="inline-links inline-links--style-2">
                                                                <li class="d-none d-lg-inline-block">
                                                                    <a href="#" class="link link--style-2 strong-600">Edit address</a>
                                                                </li>
                                                                <li>
                                                                    <a href="#" class="link link--style-2 strong-600">Add new</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <!-- FOOTER -->
                    <footer id="footer" class="footer">
                        <div class="footer-top">
                            <div class="container">
                                <div class="row cols-xs-space cols-sm-space cols-md-space">
                                    <div class="col-lg-5">
                                        <div class="col">
                                            <img src="../../assets/images/logo/logo-1-c.png">
                                            <span class="clearfix"></span>
                                            <span class="heading heading-sm c-gray-light strong-400">One template. Infinite solutions.</span>
                                            <p class="mt-3">
                                                All the components included in Boomerang are built to the same level of quality as Bootstrap and highlighted with several example pages.
                                            </p>

                                            <div class="copyright mt-4">
                                                <p>
                                                    Copyright &copy; 2018                                <a href="https://webpixels.io" target="_blank">
                                                    <strong>Webpixels</strong>
                                                </a> -
                                                All rights reserved
                                                </p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-2">
                                        <div class="col">
                                            <h4 class="heading heading-xs strong-600 text-uppercase mb-1">
                                                Support
                                            </h4>

                                            <ul class="footer-links">
                                                <li><a href="#" title="Help center">Help center</a></li>
                                                <li><a href="#" title="Discussions">Discussions</a></li>
                                                <li><a href="#" title="Contact support">Contact</a></li>
                                                <li><a href="#" title="Blog">Blog</a></li>
                                                <li><a href="#" title="Jobs">FAQ</a></li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="col-lg-2">
                                        <div class="col">
                                            <h4 class="heading heading-xs strong-600 text-uppercase mb-1">
                                                Company
                                            </h4>

                                            <ul class="footer-links">
                                                <li>
                                                    <a href="#" title="Home">
                                                        Home
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" title="About us">
                                                        About us
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" title="Services">
                                                        Services
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" title="Blog">
                                                        Blog
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" title="Contact">
                                                        Contact
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <div class="col-lg-3">
                                        <div class="col">
                                            <h4 class="heading heading-xs strong-600 text-uppercase mb-1">
                                                Get in touch
                                            </h4>

                                            <ul class="social-media social-media--style-1-v4">
                                                <li>
                                                    <a href="#" class="facebook" target="_blank" data-toggle="tooltip" data-original-title="Facebook">
                                                        <i class="fa fa-facebook"></i>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" class="instagram" target="_blank" data-toggle="tooltip" data-original-title="Instagram">
                                                        <i class="fa fa-instagram"></i>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" class="dribbble" target="_blank" data-toggle="tooltip" data-original-title="Dribbble">
                                                        <i class="fa fa-dribbble"></i>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" class="dribbble" target="_blank" data-toggle="tooltip" data-original-title="Github">
                                                        <i class="fa fa-github"></i>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </footer>
                </div>
            </div>
        </div><!-- END: st-pusher -->
    </div><!-- END: st-container -->
</div><!-- END: body-wrap -->

<!-- SCRIPTS -->
<div class="back-to-top btn-back-to-top">
    <a href="#" ><asset:image src="icons/custom/up.png"></asset:image></a>
</div>

</body>
</html>