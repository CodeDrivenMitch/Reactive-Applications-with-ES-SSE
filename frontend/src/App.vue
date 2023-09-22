<script setup lang="ts">

import Header from "@/components/Header.vue";
import {ref} from "vue";
import BankAccount from "@/components/BankAccount.vue";

const account = ref<string | null>(null)

const createAccount = async () => {
  const r = await fetch("/api/account", {method: "POST"})
  account.value = await r.text()
}

</script>

<template>
  <div class="container-fluid">
    <Header/>

    <div class="bg-white m-2 mt-4 p-2 py-4 text-center" v-if="!account">
      <h1>Welcome to AxonIQ Bank!</h1>
      <p>You can sign up for an account and then use your account.</p>
      <button type="button" class="btn btn-primary me-4" @click.prevent="createAccount()">Create an account</button>
    </div>

    <div class="bg-white m-2 mt-4 p-2 py-4 text-center" v-else>
      <BankAccount :account="account"></BankAccount>
    </div>

  </div>
</template>
