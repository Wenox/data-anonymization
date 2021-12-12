import {login, registerUser, testAdmin, testNone, testUser} from "../api/auth";

const Main = () => (
    <>
        <button onClick={() => registerUser({email: "mail@mail.com", password: "password"})
            .then(function (response) {
                console.log("Success: " + JSON.stringify(response, null, 2));
            })
            .catch(function (error) {
                console.log("Error: " + JSON.stringify(error, null, 2));
            })
            .then(function () {
                console.log("Always executed");
            })}
        >
            Register user
        </button>

        <button onClick={() => login({email: "mail@mail.com", password: "password"})
            .then(function (response) {
                console.log("Success: " + JSON.stringify(response, null, 2));
                console.log("Access token: " + response.headers['access_token']);

                if (response.status === 200 && response.headers['access_token'] && response.headers['refresh_token']) {
                    localStorage.setItem('access_token', response.headers['access_token']);
                    localStorage.setItem('refresh_token', response.headers['refresh_token']);
                    console.log('local storage was set.');
                }

            })
            .catch(function (error) {
                console.log("Error: " + JSON.stringify(error, null, 2));
            })
            .then(function () {
                console.log("Always executed");
            })
        }>
            Login user
        </button>

        <br/>

        <button onClick={() => testUser()
            .then((r) => console.log('data: ' + JSON.stringify(r, null, 2)))
            .catch((err) => {
                if (err.response && err.response.status === 403 && err.response.headers['error'].startsWith('JWT expired')) {
                    console.log("JWT expired");
                }
            })
        }>User</button>
        <button onClick={() => testAdmin().then((r) => alert(r.data)).catch((err) => alert(err))}>Admin</button>
        <button onClick={() => testNone().then((r) => alert(r.data)).catch((err) => alert(err))}>None</button>
    </>
)

export default Main;