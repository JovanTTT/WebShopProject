<template>
  <div class="home">
    <HelloWorld @search="executeSearch" />

    <img id="headerimg" src="/kutija2.JPG">

    <div id="headercont">
      <h1>Sve za Vas na jednom mestu!</h1>
      <button v-if="!isLoggedIn" @click="registration">Napravite nalog</button>
      <button v-if="!isLoggedIn" @click="login">Prijavite se</button>
      <button v-if="isLoggedIn" @click="goToProfile">Moj profil</button>
      <button v-if="isLoggedIn" @click="logout">Odjavite se</button>
    </div>

    <div id="post-new-product">
      <button v-if="isLoggedIn && userRole === 'PRODAVAC'" @click="postNewProduct">Postavite oglas</button>
    </div>

    <NewProductModal :visible="showModal" @close="showModal = false" />

    <div class="add-category-container">
      <button v-if="isLoggedIn && userRole === 'PRODAVAC'" @click="showCategoryInput = true" class="new-category-button">Dodaj novu kategoriju</button>
      <div v-if="showCategoryInput">
        <input type="text" v-model="newCategoryId" placeholder="Unesite ID kategorije">
        <input type="text" v-model="newCategoryName" placeholder="Unesite naziv kategorije">
        <button @click="addNewCategory(newCategoryId, newCategoryName)" class="new-category-button">Potvrdi</button>
      </div>
    </div>

    <div class="categories-title"><h2>Pretražite neke od kategorija:</h2></div>
    <div class="category-images">
      <img src="/KNJIZA.jpg" alt="Knjige" @click="filterByCategory('Knjige')" title="Knjige">
      <img src="/OBUCAjpg.jpg" alt="Obuća" @click="filterByCategory('Obuća')" title="Obuća">
      <img src="/TELEFON.jpg" alt="Telefoni" @click="filterByCategory('Telefoni')"  title="Telefoni">
      <img src="/lata.jpg" alt="Alat" @click="filterByCategory('Alat')"  title="Alat">
    </div>


    <div class="filters-container">

      <div class="filter-type-container">
        <h2>Tip proizvoda</h2>
        <label>
          <input type="radio" name="productType" @click="fetchProducts"> Svi proizvodi
        </label>
        <br>
        <label>
          <input type="radio" name="productType" @click="filterByType('FIKSNA')"> Fiksna cena
        </label>
        <br>
        <label>
          <input type="radio" name="productType" @click="filterByType('AUKCIJA')"> Aukcija
        </label>
      </div>

      <div class="filter-price-container">
        <h2>Filter po ceni</h2>
        <label for="priceFrom">Od:</label>
        <input type="number" id="priceFrom" v-model="priceFrom" placeholder="Minimalna cena">
        <label for="priceTo">Do:</label>
        <input type="number" id="priceTo" v-model="priceTo" placeholder="Maksimalna cena">
        <button @click="filterByPrice">Filtriraj</button>
      </div>
    </div>

    <div class="category-product-container">
      <div class="categories">
        <h2>Kategorije</h2>
        <br>
        <ul class="category-list">
          <li  class="category-link" @click="fetchProducts">Svi proizvodi</li>
          <br>
          <li v-for="category in categories" :key="category.id">
            <span class="category-link" @click="filterByCategory(category.nazivKategorije)">{{ category.nazivKategorije }}</span>
          </li>
        </ul>
      </div>

      <div class="product-container">
        <div class="row">
          <div class="col-6" v-for="product in products" :key="product.id" :product="product">
            <div class="card">
              <img :src="product.slikaProizvoda" class="card-img-top" alt="Product Image">
              <div class="card-body">
                <h5 class="card-title">{{ product.naziv }}</h5>
                <p class="card-text">Cena: {{ product.cena }} RSD</p>
                <router-link :to="'/product/' + product.id" class="btn btn-primary">Vidi više...</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="pagination">
      <button @click="previousPage" :disabled="currentPage === 0">Prethodna stranica</button>
      <button @click="nextPage">Sledeća stranica</button>
    </div>
  </div>
</template>

<script>
import HelloWorld from "@/components/HelloWorld.vue";
import NewProductModal from "@/components/NewProductModal.vue";
import axios from "axios";

export default {
  name: "HomeView",
  // Registrovanje komponente
  components: {
    HelloWorld,
    NewProductModal
  },
  data() {
    return {
      allProducts: [],//novo, search
      products: [],//paginacija proizvodi
      categories: [],//sve kategorije
      currentPage: 0,
      pageSize: 6,
      isLoggedIn: false,
      priceTo: 0,
      priceFrom: 0,
      userRole: "",
      showModal: false,
      showCategoryInput: false,
      newCategoryName: '',
      newCategoryId:1,
    };
  },
  computed: {//ako nista nije napisano ispisi sve proizvode
    displayedProducts() {
      return this.products.length > 0 ? this.products : this.allProducts;
    }
  },
  methods: {

    login: function () {

      this.$router.push("/login");
    },
    logout() {

      localStorage.removeItem('user');
      this.isLoggedIn = false;
    },
    goToProfile() {

      this.$router.push("/profile");
    },
    registration: function (){

      this.$router.push("/registration");
    },
    addNewCategory(id, naziv) {
      axios.post(`http://localhost:8080/api/category/newCategory/${id}`, { naziv: naziv }, { withCredentials: true })
          .then(response => {
            this.newCategoryId = '';
            this.newCategoryName = '';
            this.showCategoryInput = false;
          })
          .catch(error => {
            console.error('Greška prilikom dodavanja nove kategorije:', error);
          });
    },
    fetchProducts() {

      axios.get("http://localhost:8080/api/product/pages", {
        params: {
          page: this.currentPage,
          size: this.pageSize
        }
      })
          .then(response => {
            console.log(response.data);
            this.products = response.data;
          })
          .catch(error => {
            console.error("There was an error fetching the products:", error);
          });
    },
    previousPage() {

      if (this.currentPage > 0) {
        this.currentPage--;
        this.fetchProducts();
      }
    },
    nextPage() {

      this.currentPage++;
      this.fetchProducts();
    },
    fetchCategories() {

      axios.get("http://localhost:8080/api/category/categories")
          .then(response => {
            console.log(response.data);
            this.categories = response.data;
          })
          .catch(error => {
            console.error("Greska pri dobijanju svih kategorija", error);
          });
    },
    filterByPrice() {

      if (this.priceFrom !== null && this.priceTo !== null) {
        axios
            .get("http://localhost:8080/api/product/filterByPrice", {
              params: {
                priceFrom: this.priceFrom,
                priceTo: this.priceTo
              }
            })
            .then(response => {
              this.products = response.data;
            })
            .catch(error => {
              console.error("Greska pri filtriranju proizvoda po ceni:", error);
            });
      } else {
        this.fetchProducts(); // ili neki drugi fallback ako korisnik nije uneo oba polja
      }
    },
    filterByType(type) {

      axios.get("http://localhost:8080/api/product/filterByType", {
        params: {type: type}
      })
          .then(response => {
            this.products = response.data;
          })
          .catch(error => {
            console.error("Greška pri filtriranju proizvoda po tipu:", error);
          });
    },
    executeSearch(searchCriteria) {

      const { name, description } = searchCriteria;

      if(description==''){

        axios.get("http://localhost:8080/api/product/search?name=" + name)
            .then(response => {
              this.products = response.data;
            })
            .catch(error => {
              this.fetchProducts();
              console.error("Greska pri koriscenju search-a", error);
            });
      }
      else if(name==''){

        axios.get("http://localhost:8080/api/product/search?description=" + description)
            .then(response => {
              this.products = response.data;
            })
            .catch(error => {
              this.fetchProducts();
              console.error("Greska pri koriscenju search-a", error);
            });
      }else {
        const params = {
          name,
          description
        };

        axios.get("http://localhost:8080/api/product/search", {params})
            .then(response => {
              this.products = response.data;
            })
            .catch(error => {
              this.fetchProducts();
              console.error("Greska pri koriscenju search-a", error);
            });
      }
    },
    filterByCategory(categoryName) {

      axios.get("http://localhost:8080/api/product/filterByCategory", {
        params: {category: categoryName}
      })
          .then(response => {
            this.products = response.data;
            if (this.products.length === 0) {
              alert(`Trenutno nema proizvoda u kategoriji`);
            }
          })
          .catch(error => {
            console.error("Greska pri filtriranju proizvoda po kategoriji:", error);
          });
    },
    postNewProduct() {

      this.showModal = true; // Prikazivanje modala kada se klikne na "Postavite oglas"
    }
  },
  mounted() {

    this.fetchProducts();
    this.fetchCategories();

    const user = JSON.parse(localStorage.getItem('user'));

    if (user) {
      this.isLoggedIn = true;
      const userRole = localStorage.getItem("userRole");
      if (userRole) {
        this.userRole = userRole;
      }
    }

  },
};
</script>
<style scoped>
body, html {
  margin: 0;
  padding: 0;
  width: 100%;
}
.home {

  text-align: center;
  font-family: "Bodoni MT";
  width: 100%;
  margin: 0;
  padding: 0;
}
#headerimg{
  width: 100%;
  height: 60vh;
  margin: 0;
  padding: 0;

}
#headercont {
  font-family: Arial;
  position: absolute;
  top: 40%;
  left: 30%;/*65*/
  transform: translate(-50%, -50%);
  text-align: center;
  z-index: 1;
  color: rgba(47, 128, 102, 0.76);
}
#headercont button {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px 0;
  width: 100%;
  border-radius: 30px;
}
button{
  color: white;
  background-color: rgba(47, 128, 102, 0.76);
  padding: 15px 25px;
  border-radius: 5px;
  border: 1px solid gray;
  cursor: pointer;
}
button:hover {
  background-color: rgb(72, 136, 113);
}
#post-new-product {
  display: flex;
  justify-content: center;
  align-items: center;
}
#post-new-product button {
  display: block;
  width: 200px;
  padding: 10px;
  border: none;
  border-radius: 50px;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}
#post-new-product button:hover {
  background-color: #488871;
}
.filter-type-container {
  float: left;
  padding: 10px;
  color: white;
  font-family: Arial, sans-serif;
  margin-left: 60px;
  width: 500px;
  height: 120px;
  color: #966396;
  text-align: center;
}
.filter-type-container h2 {
  margin-top: 0; /* tip proizvoda */
  font-size: 20px;
}
.filter-price-container {
  font-family: Arial, sans-serif; /* Font */
  color: #966396; /* Zelena boja teksta */
}
.filter-price-container input {
  margin-right: 10px;
}
.filter-price-container button {
  width: 200px;
  border-radius: 30px;
}
.category-product-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  width: 100%;
}
.categories {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  margin-left: 100px;
  padding: 40px; /* Dodajemo padding za uokvirivanje */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Dodajemo senku */
  background-color: rgba(47, 128, 102, 0.76);
  width: 200px;
  height: fit-content;
  color: white;
  font-family: Arial;
}

.category-list {
  list-style-type: none;
  padding: 0;
}

.category-link {
  display: block;
  padding: 8px 16px; /* Prilagodite padding po potrebi */
  color: #fff; /* Promenjena boja teksta na belu */
  text-decoration: none;
  border-radius: 5px;
}

.category-link:hover {
  background-color: #79d3b2;
  cursor: pointer;
}
.product-container {
  font-family: Arial;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 20px;
  width: 100%;

}
.row {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  width: 60%;
}
.col-6 {
  flex: 0 0 calc(30% - 10px); /* Smanjenje širine kartice i postavljanje lufta od 10 piksela između kartica */
  margin: 0 5px;
  margin-bottom: 10px;



}
.card {
  width: 200px;
  height: 300px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: rgba(246, 246, 246, 0.84);
  padding: 10px;/*da ne bi odma pocela slika kad i kartica*/

}
.card-img-top {
  width: 80%;
  /* height: 200px;*/
  height: 70%;
  object-fit: cover;


}
.card-body {
  padding: 10px;
  font-size: 12px;
  overflow-wrap: break-word; /* Tekst će se prelomiti ako prelazi dimenzije kartice */
}
.card-body a.btn-primary {
  color: #44449d;
  text-decoration: none;
}

.card-body a.btn-primary:hover {
  background-color: lavender;
}
.pagination {
  margin-bottom: 20px;
}
.pagination button {
  margin-right: 210px;
  width: 200px;
  border-radius: 30px;
  height: 30px;
  font-size: 15px;
  text-align: center;

}
.pagination button:hover {
  background-color: #79d3b2;
  color: white;
}
.category-images {
  margin-bottom: 20px;
  margin-top: 20px;
  cursor: pointer;

}

.category-images img:hover {
  transform: scale(1.1);
  opacity: 0.8;
}
.categories-title{
  color: rgba(47, 128, 102, 0.76);
}
.add-category-container {
  margin: 20px 0;

}

.new-category-button {
  padding: 10px 15px;
  font-size: 16px;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
  margin-top: 10px;
}
</style>