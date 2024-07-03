<template>
<!--  <nav>-->
<!--    <router-link to="/">Home</router-link> |-->
<!--    <router-link to="/about">About</router-link>-->
<!--  </nav>-->
  <router-view/>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  }
}
</script>

<style>
#app {
  font-family: "Arial Black";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: rgba(47, 128, 102, 0.76);
}
body, html {
  margin: 0;
  padding: 0;
  width: 100%;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 50px; /*udaljenost searcha i jomija u odnosu na velicinu navigacije*/
  background-color: white;
  color: black;
}
input[type="text"] {
  padding: 6px;
  border-radius: 30px; /*uokviruje pretragu*/
  border: none; /* Uklanja okvir */
  width: 500px; /* Postavlja širinu input polja */
  background-color: #f0f0f0; /* Svetlo siva pozadina */
}
button {
  padding: 6px 12px;
  border-radius: 200px;
  background-color: rgba(47, 128, 102, 0.76);
  color: white;
  border: none;
}
button:hover {
  background-color: rgb(72, 136, 113); /* menja boju pri stavljanju misa na prijavu */
}
.brand {
  font-size: 50px; /* Postavite veličinu fonta */
  display: flex; /* Postavite fleksibilni prikaz */
  align-items: center; /* Centriraj elemente */
  font-family: "Arial Narrow";
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
</style>
