<template>
  <div>
    <div class="brand">
      <span class="letter">J</span><span class="letter">O</span><span class="letter">M</span><span class="letter">I</span>
    </div>
    <button class="btn-close" v-on:click="closeProductDetails"></button>
    <div class="product-details">
      <div class="product-image">
        <img :src="product.slikaProizvoda" alt="Product Image">
      </div>
      <div class="product-info">
        <hr color="#79d3b2">
        <h1>{{ product.naziv }}</h1>
        <hr color="#79d3b2" >
        <br>
        <br>
        <p>Opis proizvoda: {{ product.opis }}</p>
        <p>Tip prodaje: {{ product.tipProdaje}}</p>
        <div v-if="product && product.prodavac">
          <p @click="goToSellerProfile(product.prodavac.id)"  class="seller-name">Prodavac: {{ product.prodavac.korisnickoIme }}</p>
        </div>
        <p v-if="product.tipProdaje !== 'AUKCIJA'">Cena: {{ product.cena }} RSD</p>
        <div v-if="product.tipProdaje === 'AUKCIJA'">
          <hr>
          <p>Unesite vašu ponudu:</p>
          <input type="number" v-model="novaPonuda" />
        </div>
        <div class="buy-button-container">
          <button class="my-custom-button"  @click="buyProduct">Kupi proizvod</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>

</script>

<style scoped>

</style>