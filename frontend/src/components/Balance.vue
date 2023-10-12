<script setup lang="ts">

import {onMounted, ref} from "vue";
import {useResultStore} from "@/stores/result";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/stores/account";

const {account} = storeToRefs(useAccountStore())
const {setResult} = useResultStore()

const balance = ref<number | null>(null)
const balanceLoading = ref<boolean>(false)

const refreshBalance = async () => {
  balanceLoading.value = true
  await fetch(`/api/balance/${account.value}`)
      .then(r => r.text())
      .then(r => {
        balance.value = parseInt(r);
        balanceLoading.value = false
      })
      .catch(e => setResult(false, e.message))
};

onMounted(refreshBalance)
</script>

<template>
  <p>
    Balance: <span v-if="balance === null || balanceLoading">Loading...</span><span v-else>${{ balance.toFixed(2) }}</span>
  </p>
  <p>
    <button type="button" class="btn btn-primary btn-sm" @click.prevent="refreshBalance()" :disabled="balanceLoading">Refresh Balance</button>
  </p>
</template>