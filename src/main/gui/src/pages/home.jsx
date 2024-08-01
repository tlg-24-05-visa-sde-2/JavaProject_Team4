import React from "react";
import LoginSignup from "../components/LoginSignup";
import HomeProfile from "../components/HomeProfile";
import AuthService from "../utils/AuthService";
import Header from "../components/Header";
import "../assets/css/home.css";

function Home(props) {
  const isAuthenticated = props.isAuthenticated;
  
  return (
    <div className="home-wrapper">
      <Header isAuthenticated={isAuthenticated} />
      {isAuthenticated ? <HomeProfile /> : <LoginSignup />}
    </div>
  );
}

export default Home;
