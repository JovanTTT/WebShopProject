<template>
  <div>
    <HelloWorld />
    <div class="login-form">
      <input v-model="korisnik.korisnickoIme" type="text" placeholder="Korisničko ime" /><br />
      <input v-model="korisnik.lozinka" type="password" placeholder="Lozinka" /><br />
      <button v-on:click="login">Prijavi se</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import HelloWorld from "@/components/HelloWorld.vue";
export default {
  name: "LoginView",
  components: {HelloWorld},
  data: function () {
    return {
      korisnik: {},
    };
  },
  methods: {
    login: function () {
      axios
          .post("http://localhost:8080/api/user/login", this.korisnik, {
            withCredentials: true,//zbog sesije
          })
          .then((res) => {
            console.log("Prijavljen korisnik:", this.korisnik.korisnickoIme);
            localStorage.setItem("user", JSON.stringify(res.data.id));
            localStorage.setItem("userUSER", JSON.stringify(res.data));
            localStorage.setItem("userRole", res.data.uloga); // Čuvanje uloge korisnik
            if(res.data.uloga === 'ADMINISTRATOR'){
              this.$router.push('/admin');
            }else{
              this.$router.push("/");
            }
            this.$emit("userLoggedIn");
          })
          .catch((err) => {
            console.log(err);
            alert("Došlo je do greške");
          });

    },
  },
};
</script>

<style>
.login-form {
  display: flex;
  flex-direction: column;
  width: 300px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ffffff;
  border-radius: 5px;
  background-color: #ffffff;
  box-shadow: 0 2px 5px rgba(47, 128, 102, 0.76);
  align-items: center;
  margin-top: 100px;
}

.login-form label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.login-form input[type="text"],
.login-form input[type="password"] {
  width: 90%;
  padding: 10px;
  border: 1px solid #f8cdf8;
  border-radius: 3px;
  margin-bottom: 10px;
  background-color: rgba(224, 184, 224, 0.93);
}

.login-form input[type="text"]:focus,
.login-form input[type="password"]:focus {
  outline: none;
  border: 1px solid rgba(47, 128, 102, 0.76);
}

.login-form button {
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-form button:hover {
  background-color: rgb(72, 136, 113);
}
</style>



