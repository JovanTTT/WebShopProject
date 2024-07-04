<template>
  <div>
    <HelloWorld />
    <div class="registration-form">
      <div class="column">
        <label for="ime">Ime:</label>
        <input v-model="korisnik.ime" type="text" id="ime" placeholder="Ime" />

        <label for="prezime">Prezime:</label>
        <input v-model="korisnik.prezime" type="text" id="prezime" placeholder="Prezime" />

        <label for="korisnickoIme">Korisničko ime:</label>
        <input v-model="korisnik.korisnickoIme" type="text" id="korisnickoIme" placeholder="Korisničko ime" />
      </div>

      <div class="column">
        <label for="mejl">Mejl:</label>
        <input v-model="korisnik.mejl" type="text" id="mejl" placeholder="Mejl" />

        <label for="telefon">Telefon:</label>
        <input v-model="korisnik.telefon" type="text" id="telefon" placeholder="Telefon" />

        <label for="lozinka">Lozinka:</label>
        <input v-model="korisnik.lozinka" type="password" id="lozinka" placeholder="Lozinka" />
      </div>

      <div class="column">
        <label for="ponovljenaLozinka">Ponovljena lozinka:</label>
        <input v-model="korisnik.ponovljenaLozinka" type="password" id="ponovljenaLozinka" placeholder="Ponovljena lozinka" />

        <label for="datumRodjenja">Datum rođenja:</label>
        <input v-model="korisnik.datumRodjenja" type="date" id="datumRodjenja" placeholder="Datum rođenja" />

        <label for="uloga">Izaberite ulogu:</label>
        <select v-model="korisnik.uloga" id="uloga" name="uloge" required>
          <option value="PRODAVAC">Prodavac</option>
          <option value="KUPAC">Kupac</option>
        </select>
      </div>

      <button v-on:click="registration">Registruj se</button>
    </div>
    <div v-if="showErrorModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeErrorModal">&times;</span>
        <h2>Greška</h2>
        <p>{{successMessage }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import HelloWorld from "@/components/HelloWorld.vue";
export default {
  name: "RegistrationView",
  components: {HelloWorld},
  data: function () {
    return {
      korisnik: {},//ovo dobijam prilikom prijave, posto to posle prosledjujem pomocu url mora da bude isto kao polja u
      //login dto klasi
      showErrorModal: false,
      successMessage: ''
    };
  },
  methods: {
    registration: function () {
      axios
          .post("http://localhost:8080/api/user/registration", this.korisnik, {
            withCredentials: true,//zbog sesije
          })
          .then((res) => {
            console.log(res);//javlja da je dobro i stavlja me na home stranicu
            this.$router.push("/");
          })
          .catch((error) => {
            if (error.response && error.response.data === "Korisnik sa ovim email-om već postoji.") {
              this.successMessage= "Korisnik sa ovim email-om već postoji.";
              this.showErrorModal = true;
            }else if(error.response && error.response.data === "Korisnik sa ovim korisnickim imenom vec postoji."){
              this.successMessage= "Korisnik sa ovim korisnickim imenom vec postoji.";
              this.showErrorModal = true;
            }else if(error.response && error.response.data ===  "Lozinke se ne poklapaju."){
              this.successMessage= "Lozinke se ne poklapaju.";
              this.showErrorModal = true;
            }else{
              console.log(error);
            }
          });

    },
    closeErrorModal() {
      this.showErrorModal = false; // Zatvaranje grešnog modalnog prozora
    },
  },
};
</script>

<style scoped>

</style>