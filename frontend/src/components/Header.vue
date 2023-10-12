<script setup lang="ts">

import {storeToRefs} from "pinia";
import {useAccountStore} from "@/stores/account";

const {account} = storeToRefs(useAccountStore())
const {logout} = useAccountStore()
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
        <a href="#" @click.prevent="logout" v-if="account">Logout</a>
      </div>
    </div>
  </nav>
</template>