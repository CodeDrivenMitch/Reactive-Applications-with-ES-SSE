import {createApp} from 'vue'

import App from './App.vue'

import './styles.scss'
import './icons.css'
import "bootstrap"

const app = createApp(App)

app.mount('#app')


window.setDelay = function (delay) {
    fetch('/api/delay/' + delay, {method: 'PUT'})
}