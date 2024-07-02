<template>
  <nav class="navbar">
    <div class="search-container">
      <input v-model="searchQuery" type="text" placeholder="Pretraga..."  title="Za pretraživanje koristiti sledeće pravilo: po nazivu (naziv), po opisu (;opis), po nazivu i opisu (naziv;opis)"  class="search-input"><!--ono sto korisnik posalje, sada je string-->
      <!--<button type="submit" class="search-button">
        <img src="@/assets/pretraga.webp" alt="Pretraga">
      </button>-->
      <button type="submit" class="search-button"  @click="onSearch">
        <!--Pretraži-->
        <img src="/pretraga.webp" alt="Pretraga">
      </button>
    </div>
    <div class="brand">
      <span class="letter">J</span><span class="letter">O</span><span class="letter">M</span><span class="letter">I</span>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      searchQuery: ''
    };
  },
  methods: {
    onSearch() {
      const query = this.searchQuery.trim(); // Remove leading/trailing whitespace
      console.log(query);
      // Send the entire query if there's only one word
      if (query.split(';').length === 1) {
        this.$emit('search', { name: query, description: '' });
      }else if(query[0]==':'){
        this.$emit('search', { name: '', description:query.split(';')[1] });//sve posle ;
      }
      else {
        // Split the query into separate name and description
        const name = query.split(';')[0];
        const description = query.split(';')[1];
        this.$emit('search', { name, description });
      }
    }
  }
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 50px; /*udaljenost searcha i jomija u odnosu na velicinu navigacije*/
  background-color: rgba(176, 234, 211, 0.5);
  color: black;
  /*width: 70%;  da ne ide preko celog ekrana*/
  margin: 0 auto;
  transition: top 0.3s; /* Dodaj smooth tranziciju za glatko spuštanje */

  max-width: 95%; /* Maksimalna širina, da ne ide preko celog ekrana */
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000; /* Postavi visok z-index da bi bio iznad ostalih sadržaja */
  transition: top 0.3s; /* Dodaj smooth tranziciju za glatko spuštanje */
}
input[type="text"] {
  padding: 6px;
  border-radius: 30px; /*uokviruje pretragu*/
  border: none; /* Uklanja okvir */
  width: 500px; /* Postavlja širinu input polja */
  background-color: #f0f0f0; /* Svetlo siva pozadina */
}
.search-label{
  font-family: Arial;
  background-color: #f0f0f0;
  padding: 6px;
  border-radius: 30px; /*uokviruje pretragu*/
  border: none; /* Uklanja okvir */
}

button {
  padding: 6px 12px;
  border-radius: 200px;
  background-color:#f0f0f0;
  color: #000000;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: rgb(206, 206, 213); /* menja boju pri stavljanju misa na prijavu */
}
button img {
  width: 15px; /* Postavite željenu širinu slike */
  height: 14px; /* Postavite željenu visinu slike */
}
.brand {
  font-size: 50px; /* Postavite veličinu fonta */
  display: flex; /* Postavite fleksibilni prikaz */
  align-items: center; /* Centriraj elemente */
  font-family: Arial;
}

.letter:nth-child(1) {
  color: #773577 /* Boja slova J */
}
.letter:nth-child(2) {
  color: #4141da; /* Boja slova O */
}
.letter:nth-child(3) {
  color: #ffb4f4; /* Boja slova M */
}
.letter:nth-child(4) {
  color: #488871; /* Boja slova I */
}
.search-container {
  position: relative;
  width: 100%;
  max-width: 500px; /* prilagodite po potrebi */
}

.search-input {
  width: 100%;
  padding: 10px 40px 10px 10px; /* dodajte padding za desni deo gde će biti ikona */
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box; /* da padding ne utiče na ukupnu širinu */
  padding: 10px 40px 10px 10px; /* dodajte padding za desni deo gde će biti ikona */
}

.search-button {
  position: absolute;
  right: 10px; /* prilagodite po potrebi */
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
}

.search-icon {
  width: 20px; /* prilagodite po potrebi */
  height: 20px; /* prilagodite po potrebi */
  right: 10px; /* prilagodite po potrebi */
}
</style>