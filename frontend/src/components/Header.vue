<script setup lang="ts">
import {ref, watchEffect} from "vue";

const delay = ref(-1)

watchEffect(() => {
  if(delay.value < 0) {
    return
  }
  console.log(delay.value)

  fetch('/api/delay/' + delay.value, {method: 'PUT'})
})

fetch('/api/delay')
    .then(r => r.text())
    .then(r => delay.value = parseInt(r))
    .catch(e => console.error(e))
</script>

<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-white">
    <div class="container-fluid px-3">
      <a class="navbar-brand" href="/"><img src="../assets/logo.svg" alt="" width="30" height="24"> AxonIQ Bank Demo</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="navbar-nav">
        <select class="form-select" aria-label="Default select example" v-model="delay">
          <option value="0">No Delay</option>
          <option value="20">20ms</option>
          <option value="50">50ms</option>
          <option value="100">100ms</option>
          <option value="200">200ms</option>
          <option value="500">500ms</option>
          <option value="1000">1000ms</option>
          <option value="2000">2000ms</option>
        </select>
      </div>
    </div>
  </nav>
</template>