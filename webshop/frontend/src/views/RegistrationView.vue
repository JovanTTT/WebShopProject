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
              this.successMessage = "Korisnik sa ovim email-om već postoji.";
              this.showErrorModal = true;
            } else if (error.response && error.response.data === "Korisnik sa ovim korisnickim imenom vec postoji.") {
              this.successMessage = "Korisnik sa ovim korisnickim imenom vec postoji.";
              this.showErrorModal = true;
            } else if (error.response && error.response.data === "Lozinke se ne poklapaju.") {
              this.successMessage = "Lozinke se ne poklapaju.";
              this.showErrorModal = true;
            } else {
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

<style>

.registration-form {
  display: flex;
  justify-content: space-around;
  align-items: flex-start; /* Polja počinju od vrha */
  flex-wrap: wrap; /* Omogućava da se redovi prelome ako nema dovoljno širine */
  width: 100%;
  max-width: 800px; /* Ograničava širinu formulara */
  margin: 50px auto;
  padding: 20px;
  border: 1px solid rgba(47, 128, 102, 0.76);
  border-radius: 5px;
  background-color: #ffffff;
  box-shadow: 0 2px 5px rgba(47, 128, 102, 0.76);
  margin-top: 100px;

}

.column {
  flex: 1; /* Ravnomerno raspoređuje prostor između kolona */
  margin-right: 20px; /* Razmak između kolona */
}

.column:last-child {
  margin-right: 0; /* Poslednja kolona nema desni razmak */
}

.registration-form label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.registration-form input[type="text"],
.registration-form input[type="password"],
.registration-form input[type="date"],
.registration-form select {
  width: 100%; /* Popunjava celu širinu kolone */
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #e0b8e0;
  border-radius: 3px;
  box-sizing: border-box;
  //background-color: #d8d8f6;
}

.registration-form button {
  width: 100%; /* Popunjava celu širinu formulara */
  padding: 12px 20px;
  margin-top: 20px; /* Razmak između polja i dugmeta */
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.registration-form button:hover {
  background-color: rgb(72, 136, 113);
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 5px;
  width: 90%;
  max-width: 500px;
  text-align: center;
}

.modal-content .close {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
  font-size: 1.5em;
  color: #aaa;
}

.modal-content .close:hover {
  color: #000;
}

</style>