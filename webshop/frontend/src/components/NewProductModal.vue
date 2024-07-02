<template>
  <div class="modal" v-if="visible">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <h2>Postavite novi proizvod</h2>
      <form @submit.prevent="postProduct">
        <div class="form-group">
          <label for="naziv">Naziv:</label>
          <input type="text" id="naziv" v-model="proizvodDTO.naziv" required>
        </div>
        <div class="form-group">
          <label for="opis">Opis:</label>
          <textarea id="opis" v-model="proizvodDTO.opis" required></textarea>
        </div>
        <div class="form-group">
          <label for="cena">Cena:</label>
          <input type="number" id="cena" v-model="proizvodDTO.cena" required  min="1">
        </div>
        <div class="form-group">
          <label for="tipProdaje">Tip prodaje:</label>
          <select id="tipProdaje" v-model="proizvodDTO.tipProdaje" required>
            <option value="FIKSNA">Fiksna cena</option>
            <option value="AUKCIJA">Aukcija</option>
          </select>
        </div>
        <div class="form-group">
          <label for="slikaProizvoda">URL slike proizvoda:</label>
          <input type="text" id="slikaProizvoda" v-model="proizvodDTO.slikaProizvoda">
        </div>
        <div class="form-group">
          <label for="kategorije">Kategorija</label>
          <input type="text" id="kategorije" v-model="kategorijeInput" required>
        </div>
        <button type="submit" class="submit-button">Postavi proizvod</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "NewProductModal",
  props: {
    visible: Boolean
  },
  data() {
    return {
      proizvodDTO: {
        naziv: '',
        opis: '',
        slikaProizvoda: '',
        kategorije: [],
        cena: 0,
        tipProdaje: 'FIKSNA'
      },
      kategorijeInput: ''
    };
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    postProduct() {
      this.proizvodDTO.kategorije = this.kategorijeInput.split(',').map(kategorija => ({ nazivKategorije: kategorija.trim() }));

      axios.post('http://localhost:8080/api/product/addForSale', this.proizvodDTO, { withCredentials: true })
          .then(response => {
            console.log(response.data);
            this.closeModal();
          })
          .catch(error => {
            console.error('Greška pri postavljanju proizvoda:', error);
          });
    }
  }
};
</script>

<style scoped>
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
  color: #773577;
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
  max-width: 600px;
  border-radius: 10px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input, textarea, select {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 50px;
}

textarea {
  resize: vertical;
}

.submit-button {
  padding: 10px 20px;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  font-size: 1em;

}

.submit-button:hover {
  background-color: #488871;
}
</style>