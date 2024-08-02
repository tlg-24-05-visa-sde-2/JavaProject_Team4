import React from "react";

function Shutdown() {
  function handleShutdown() {
    console.log("Shutting down server...");

    window.location.replace("/shutdown");
  }

  return (
    <div>
      <button className="shutdown-btn" onClick={() => handleShutdown()}>Shutdown Application</button>
    </div>
  );
}

export default Shutdown;