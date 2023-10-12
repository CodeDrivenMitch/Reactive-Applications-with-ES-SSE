import {createApp} from 'vue'

import App from './App.vue'

import './styles.scss'
import './icons.css'
import "bootstrap"
import {createPinia} from "pinia";

const app = createApp(App)

const pinia = createPinia()
app.use(pinia)
app.mount('#app')