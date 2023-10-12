<script setup lang="ts">

import {ref} from "vue";
import {useResultStore} from "@/stores/result";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/stores/account";

const {account} = storeToRefs(useAccountStore())
const {setResult} = useResultStore()

const input = ref<number | null>(20)
const loading = ref<boolean>(false)


const deposit = async () => {
  loading.value = true
  await fetch(`/api/account/${account.value}/deposit`, {
    method: 'POST',
    body: JSON.stringify({amount: input.value}),
    headers: {'Content-Type': 'application/json'}
  }).then(async r => {
    if (!r.ok) {
      setResult(false, await r.text());
    } else {
      setResult(true, `Successfully deposited ${input.value} dollars!`);
    }
    loading.value = false
  })
};
</script>

<template>
  <div>
    <div class="mb-3">
      <label for="inputDeposit" class="form-label">Deposit</label>
      <input id="inputDeposit" type="number" class="form-control" v-model="input">
    </div>
    <div>
      <button type="button" class="btn btn-primary" @click.prevent="deposit()" :disabled="loading">Deposit
      </button>
    </div>
  </div>
</template>