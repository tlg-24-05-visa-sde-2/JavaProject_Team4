import React from "react";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

function ShutdownPage() {

 function handleShutdown() {
    setTimeout(async () => {
      console.log("Sending shutdown request...");
      const resposne = await fetch("http://localhost:8080/admin/shutdown", {
        method: "GET",
        headers: {
          "Content-type": "application/json",
        },
      });
      const message = await resposne.text();
      console.log(message)
      window.close();
    }, 3000);
  }

  React.useEffect(() => {
    handleShutdown();
  }, []);

  return (
    <div className="shutdown-page">
      <Card className="text-center custom-card">
        <Card.Header><strong>NOTICE</strong></Card.Header>
        <Card.Body>
          <Card.Title>Application Closing</Card.Title>
          <Card.Text>
              Please close this tab. The application is shutting down. Thanks for using our app!
          </Card.Text>
        </Card.Body>
        <Card.Footer className="text-muted">&copy; Byte Bandits</Card.Footer>
      </Card>
    </div>
  );
}

export default ShutdownPage;