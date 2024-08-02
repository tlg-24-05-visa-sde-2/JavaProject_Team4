import React from "react";
import LoginSignup from "../components/LoginSignup";
import HomeProfile from "../components/HomeProfile";
import AuthService from "../utils/AuthService";
import Header from "../components/Header";
import "../assets/css/home.css";
import Shutdown from "../components/Shutdown";

function Home(props) {
  const isAuthenticated = props.isAuthenticated;

  return (
    <div className="home-wrapper">
      <Header isAuthenticated={isAuthenticated} />
      {isAuthenticated ? <HomeProfile /> : <LoginSignup />}
      <Shutdown />
    </div>
  );
}

export default Home;
