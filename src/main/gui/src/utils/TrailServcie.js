import { toast } from "react-toastify";

class TrailService {
    static async getTrails() {
        const response = await fetch("http://localhost:8080/trail/getAllHikingTrails", {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
            },
        });

        if (response.status === 200) {
            const trails = await response.json();
            return trails;
        } else {
            return { error: "An error occurred while fetching trail data" };
        }
    }

    static async favoriteTrail(trailData) {
        const response = await fetch("http://localhost:8080/user/saveTrail", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify(trailData),
        });

        const res = await response.text();
        console.log(res);
        if(response.status === 200) {
            return true;
        } else {
            return false;
        }
    }

    static async unfavoriteTrail(trailId) {
        const response = await fetch(`http://localhost:8080/user/removeTrail/${trailId}`, {
            method: "DELETE",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
            },
        });

        const res = await response.text();
        console.log(res);
        if(response.status === 200) {
            return true;
        } else {
            return false;
        }
    }
}

export default TrailService;