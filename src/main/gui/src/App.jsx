/*
 * Welcome to the app.jsx, this portion leverages the BrowserRouter from React Router Dom.
 * We have also included the react toastify library for you to add toast notifications anywhere throught the application
 * 
 * Here you can add routes as needed or you can change the router type you are using from the react router dom library,
 * look here for more router types https://reactrouter.com/en/main/routers/picking-a-router
*/

import React from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom"
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Home, Login, Signup, FindTrails } from './pages/index';
import AuthService from "./utils/AuthService";

function App() {
  const [isAuthenticated, setIsAuthenticated] = React.useState(true);
  console.log(isAuthenticated); // This will log true or false depending on whether the user is logged in or not
  
  React.useEffect(() => {
    AuthService.checkLogin().then((response) => {
      setIsAuthenticated(response);
    });
  });
  
  return (
    <Router>
      <ToastContainer theme="colored" autoClose={2000} />
      <Routes>
        <Route path="/" element={<Home isAuthenticated={isAuthenticated} />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/login" element={<Login />} />
        <Route path="/find-trails" element={<FindTrails isAuthenticated={isAuthenticated} />} />
      </Routes>
    </Router>
  );
}

export default App;