<template>
  <div>
    <div class="naslov"><h1>Pregled svih recenzija</h1></div>

    <div>
      <button class="logout-dugme" @click="logout">Odjavite se</button>
    </div>

    <div class="review-container">
      <div v-for="review in paginatedReviews()" :key="review.id" class="review-card">
        <p>Recenziju podneo: {{review.recenzijuPodneo.ime}} {{review.recenzijuPodneo.prezime}} "{{review.recenzijuPodneo.korisnickoIme}}"</p>
        <p>Recenziju primio: {{review.recenzijuPrimio.ime}} {{review.recenzijuPrimio.prezime}} "{{review.recenzijuPrimio.korisnickoIme}}"</p>
        <p>Ocena: {{ review.ocena }}</p>
        <p>Komentar: {{ review.komentar }}</p>
        <p>Datum podnošenja recenzije: {{ formatDate(review.datumPodnosenjaRecenzije) }}</p>
        <div class="button-container">
          <button class="review-button update" @click="toggleUpdateForm(review.id)">Ažuriraj</button>
          <button class="review-button delete" @click="deleteReview(review.id)">Obriši</button>
        </div>
        <div v-if="review.showUpdateForm" class="update-form">
          <label for="newRating">Nova ocena:</label><br>
          <input type="number" v-model="review.newRating" min="1" max="5"><br>
          <label for="newComment">Novi komentar:</label><br>
          <textarea v-model="review.newComment"></textarea>
          <button class="review-button save" @click="saveReview(review.id)">Sačuvaj</button>
        </div>
      </div>
    </div>

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">Prethodna</button>
      <span>Stranica {{ currentPage }}</span>
      <button @click="nextPage" :disabled="currentPage * itemsPerPage >= reviews.length">Sledeća</button>
    </div>


  </div>
</template>

<script setup>

</script>

<style scoped>

</style>