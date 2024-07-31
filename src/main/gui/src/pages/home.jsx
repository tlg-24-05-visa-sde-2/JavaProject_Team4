import React from "react";
import "../assets/css/home.css";
import LoginSignup from "../components/LoginSignup";

function Home() {
  return (
    <div className="home-wrapper">
      <h1 className="text-center">Go take a hike!</h1>
      <LoginSignup />
    </div>
  );
}

export default Home;