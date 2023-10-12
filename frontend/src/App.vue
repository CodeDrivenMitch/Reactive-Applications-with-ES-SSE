<script setup lang="ts">

import Header from "@/components/Header.vue";
import BankAccount from "@/BankAccount.vue";
import Messages from "@/components/Messages.vue";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/stores/account";

const {account} = storeToRefs(useAccountStore())
const {createAccount} = useAccountStore()
</script>

<template>
  <div class="container-fluid">
    <Header/>
    <Messages/>

    <div class="bg-white m-2 mt-4 p-2 py-4 text-center" v-if="!account">
      <h1>Welcome to AxonIQ Bank!</h1>
      <p>You can sign up for an account and then use your account.</p>
      <button type="button" class="btn btn-primary me-4" @click.prevent="createAccount()">Create an account</button>
    </div>

    <div class="bg-white m-2 mt-4 p-2 py-4 text-center" v-else>
      <BankAccount></BankAccount>
    </div>

  </div>
</template>
