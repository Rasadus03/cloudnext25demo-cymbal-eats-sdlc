// src/components/Header.js
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';


function Header({ cartItemCount , toogle, toogleFace, toogleEmail}) {
    const [pwd, setPwd] = useState('');
    const [username, setUser] = useState('');
  return (
    <header className="app-header">
      <script src="https://www.gstatic.com/firebasejs/7.18/firebase-app.js"></script>
      <script src="https://www.gstatic.com/firebasejs/7.18/firebase-auth.js"></script>
      <script src="https://www.gstatic.com/firebasejs/8.0/firebase.js"></script>
      <script src="https://www.gstatic.com/firebasejs/ui/6.1.0/firebase-ui-auth.js"></script>
      <script src="https://www.gstatic.com/firebasejs/ui/6.1.0/firebaseui.js"></script>
      <link type="text/css" rel="stylesheet" href="https://www.gstatic.com/firebasejs/ui/6.1.0/firebase-ui-auth.css" />
      <link type="text/css" rel="stylesheet" href="https://www.gstatic.com/firebasejs/ui/6.1.0/firebaseui.css" />
      <div className="logo">
      <a href="/" className="logo"><img className="imageLogo" src="../cymbaleats1.png" alt="Logo"/> </a>
      </div>


      <div className="signin-button">
      <button className="signin-button" id="signInButton" onClick={()=>toogle()}>Sign In Google</button>
      </div><div className="signin-button">
      <button className="signin-button" id="signInButton2" onClick={()=>toogleFace()}>Sign In Facebook</button>
      
      </div>
      <label htmlFor="city">username:</label>
          <input
              type="text"
              id="username"
              onChange={(e) => setUser(e.target.value)}
              required
          />
       <label htmlFor="city">Password:</label>
          <input
              type="password"
              id="pwd"
              onChange={(e) => setPwd(e.target.value)}
              required
          />
      <div className="signin-button">
       
      <button className="signin-button" id="signInButton3" onClick={()=>toogleEmail(username, pwd)}>Sign In Email/Pwd</button>
      
      </div>
        <nav>

        <ul>
          <li><Link to="/restaurants">Restaurants</Link></li>
          <li>
            <Link to="/cart">
              My Cart ({cartItemCount})
            </Link>
          </li>
          <li><Link to="/orders">My Orders</Link></li>
        </ul>
      </nav>
    </header>
  );
}
//1970331557106131
//059984335ca6f7ec330cc4dbfb5aee46
Header.propTypes = {
  cartItemCount: PropTypes.number.isRequired,
  toogle: PropTypes.func.isRequired,
  toogleFace: PropTypes.func.isRequired,
  toogleEmail: PropTypes.func.isRequired,
};

export default Header;
