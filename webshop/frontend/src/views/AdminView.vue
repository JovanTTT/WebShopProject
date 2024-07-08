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

    <div class="naslov2">
      <h1>Pregled svih prijava</h1>
    </div>

    <div class="review-container">
      <div v-for="report in paginatedReports()" :key="report.id" class="review-card">
        <p>Prijavu podneo: {{report.podnosiocPrijave.ime}} {{report.podnosiocPrijave.prezime}} "{{report.podnosiocPrijave.korisnickoIme}}"</p>
        <p>Prijavu primio: {{report.prijavljeniKorisnik.ime}} {{report.prijavljeniKorisnik.prezime}} "{{report.prijavljeniKorisnik.korisnickoIme}}"</p>
        <p>Razlog prijave: {{ report.razlogPrijave }}</p>
        <p>Status: {{ report.statusPrijave }}</p>
        <p>Datum podnošenja prijave: {{ formatDate(report.datumPodnosenjaPrijave) }}</p>
        <div class="button-container">
          <button class="review-button update" @click="accept(report.id)">Prihvati</button>
          <button class="review-button delete" @click="toggleRejectionForm(report.id)">Odbij</button>
        </div>
        <div v-if="report.showRejectionForm" class="rejection-form">
          <label for="rejectionReason">Razlog odbijanja:</label><br>
          <textarea v-model="report.razlogOdbijanja"></textarea>
          <button class="review-button save" @click="reject(report.id)">Potvrdi</button>
        </div>
      </div>
    </div>

    <div class="pagination">
      <button @click="prevPageReport" :disabled="currentPageReport === 1">Prethodna</button>
      <span>Stranica {{ currentPageReport }}</span>
      <button @click="nextPageReport" :disabled="currentPageReport * itemsPerPageReport >= reports.length">Sledeća</button>
    </div>
  </div>

</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
      reviews: [],
      currentPage: 1, // Trenutna stranica
      itemsPerPage: 4, // Broj kartica po stranici
      reports: [],
      currentPageReport: 1, // Trenutna stranica
      itemsPerPageReport: 4, // Broj kartica po stranici
    };
  },
  mounted() {

    this.fetchReviews();
    this.fetchReports();
  },
  methods: {
    fetchReviews() {

      axios
          .get('http://localhost:8080/api/user/reviews/admin', {withCredentials: true})
          .then(response => {
            this.reviews = response.data;
          })
          .catch(error => {
            console.error('Greška pri dobavljanju podataka o recenzijama:', error);
          });
    },
    fetchReports(){

      axios
          .get('http://localhost:8080/api/report/allReports', {withCredentials: true})
          .then(response => {
            this.reports = response.data;
          })
          .catch(error => {
            console.error('Greška pri dobavljanju podataka o prijavama:', error);
          });
    },
    formatDate(dateString) {

      const options = { year: 'numeric', month: 'long', day: 'numeric' };

      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    deleteReview(reviewId) {

      axios
          .delete(`http://localhost:8080/api/user/deleteReview/${reviewId}`, { withCredentials: true })
          .then(response => {
            console.log('Recenzija obrisana:', response);
            this.reviews = this.reviews.filter(review => review.id !== reviewId);
          })
          .catch(error => {
            console.error('Greška pri brisanju recenzije:', error.response.data);
            alert('Greška pri brisanju recenzije: ' + error.response.data.message);
          });
    },
    paginatedReviews() {

      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;

      return this.reviews.slice(start, end);
    },
    paginatedReports() {

      const start = (this.currentPageReport - 1) * this.itemsPerPageReport;
      const end = start + this.itemsPerPageReport;

      return this.reports.slice(start, end);
    },
    nextPage() {

      if (this.currentPage * this.itemsPerPage < this.reviews.length) {
        this.currentPage++;
      }
    },
    nextPageReport() {

      if (this.currentPageReport * this.itemsPerPageReport < this.reports.length) {
        this.currentPageReport++;
      }
    },
    prevPage() {

      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    prevPageReport() {

      if (this.currentPageReport > 1) {
        this.currentPageReport--;
      }
    },
    toggleUpdateForm(reviewId) {

      this.reviews = this.reviews.map(review => {
        if (review.id === reviewId) {
          review.showUpdateForm = !review.showUpdateForm;
          if (review.showUpdateForm) {
            review.newRating = review.ocena;
            review.newComment = review.komentar;
          }
        }
        return review;
      });
    },
    toggleRejectionForm(reportId) {

      this.reports = this.reports.map(report => {
        if (report.id === reportId) {
          report.showRejectionForm = !report.showRejectionForm;
          if (report.showRejectionForm) {
            report.rejectionReason = '';
          }
        }
        return report;
      });
    },
    accept(reportId) {

      axios.post(`http://localhost:8080/api/report/adminAcceptReport/${reportId}`, {}, {
        withCredentials: true,
        headers: {
          'Content-Type': 'application/json'
        }
      })
          .then(response => {
            console.log('Prijava prihvaćena:', response.data);
            // Ažurirajte status prijave na lokalnoj listi
            this.reports = this.reports.map(report =>
                report.id === reportId ? {...report, statusPrijave: 'PRIHVACENA'} : report
            );
          })
          .catch(error => {
            console.error('Greška pri prihvatanju prijave:', error);
            alert('Prijava je vec obradjena!');
          });
    },
    reject(reportId) {

      const reportToReject = this.reports.find(report => report.id === reportId);
      const rejectionData = {
        razlogOdbijanja: reportToReject.rejectionReason
      };

      axios
          .post(`http://localhost:8080/api/report/adminRejectionReport/${reportId}`, rejectionData, {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            console.log('Prijava odbijena:', response);
            // Ažurirajte status prijave u lokalnoj listi
            this.reports = this.reports.map(report =>
                report.id === reportId ? {...report, statusPrijave: 'ODBIJENA', showRejectionForm: false} : report
            );
          })
          .catch(error => {
            console.error('Greška pri odbijanju prijave:', error);
            alert('Prijava je vec obradjena!');
          });
    },
    saveReview(reviewId) {

      const reviewToUpdate = this.reviews.find(review => review.id === reviewId);

      const updateData = {
        id: reviewId,
        ocena: reviewToUpdate.newRating,
        komentar: reviewToUpdate.newComment
      };

      axios
          .put(`http://localhost:8080/api/user/updateReview/${reviewId}`, updateData, {
            withCredentials: true,
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            // Osvježite listu recenzija sa ažuriranim podacima
            this.reviews = this.reviews.map(review =>
                review.id === reviewId ? {
                  ...review,
                  ocena: reviewToUpdate.newRating,
                  komentar: reviewToUpdate.newComment,
                  showUpdateForm: false
                } : review
            );
            console.log('Recenzija ažurirana:', response.data);
          })
          .catch(error => {
            console.error('Greška pri ažuriranju recenzije:', error);
            alert('Greška pri ažuriranju recenzije: ' + error.response.data.message);
          });

      console.log('ID recenzije za čuvanje:', reviewId);
    },
    logout() {
      localStorage.removeItem('user');
      this.$router.push("/");
    },
  }
}

</script>

<style>

.naslov {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px; /* Promenjeno na margin-bottom */
  color: #c963c9;
}

.naslov2 {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 50px;
  margin-bottom: 10px;
  color: #c963c9;
}

.review-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding: 30px;
}

.review-card {
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.review-button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.review-button.update {
  background-color: #44449d; /* Zelena boja za Ažuriraj */
  color: white; /* Bela boja teksta */
}

.review-button.delete {
  background-color: #c963c9; /* Crvena boja za Obriši */
  color: white; /* Bela boja teksta */
}

.review-button.save {
  background-color: #ffb4f4;
  color: white;
  margin-top: 0;
  margin-left: 50px;
  margin-bottom: 10px;
}

.update-form {
  margin-top: 40px;
}

.pagination {
  display: flex;
  justify-content: right;
  align-items: center;
  margin-top: 20px;
  margin-right: 50px;
  margin-bottom: 30px;
}

.pagination button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  background-color: #488871;
}

.pagination span {
  font-size: 18px;
}

.logout-dugme {
  background-color: #488871;
  margin-left: 50px;
  margin-top: -60px;
}


</style>