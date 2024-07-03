import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
// import ProductView from "@/views/ProductView.vue";
// import RegistrationView from "@/views/RegistrationView.vue";
// import HomePageView from "@/views/HomePageView.vue";
// import ProfileView from "@/views/ProfileView.vue";
// import SellerProfileView from "@/views/SellerProfileView.vue";
// import RecenzijeView from "@/views/RecenzijeView.vue";
// import CustomerProfileView from "@/views/CustomerProfileView.vue";
// import AdminView from "@/views/AdminView.vue";




Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  // {
  //   path: '/registration',
  //   name: 'registration',
  //   component: RegistrationView
  //   //neibtno
  // },
  // {
  //   path: '/homepage',
  //   name: 'homepage',
  //   component: HomePageView
  // },
  // {
  //   path: '/profile',
  //   name: 'profile',
  //   component: ProfileView
  // },
  // {
  //   path: '/admin',
  //   name: 'admin',
  //   component: AdminView
  // },
  // // {
  // //   path: '/profile/:id', // Definicija dinamičke rute za detalje proizvoda
  // //   name: 'profile',
  // //   component: ProfileView,
  // //   props: true // Prosleđivanje parametara kao propertija komponenti
  // // },
  // {
  //   path: '/product/:id', // Definicija dinamičke rute za detalje proizvoda
  //   name: 'product',
  //   component: ProductView,
  //   props: true // Prosleđivanje parametara kao propertija komponenti
  // },
  // {
  //   path: '/sellerProfile/:id',
  //   name: 'sellerProfile',
  //   component: SellerProfileView,
  //   props: true
  // },
  // {
  //   path: '/customerProfile/:id',
  //   name: 'customerProfile',
  //   component: CustomerProfileView,
  //   props: true
  // }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
