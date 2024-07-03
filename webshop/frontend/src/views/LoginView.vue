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

</style>