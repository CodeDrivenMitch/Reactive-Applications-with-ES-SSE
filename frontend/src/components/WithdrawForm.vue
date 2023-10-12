<script setup lang="ts">

import {ref} from "vue";
import {useResultStore} from "@/stores/result";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/stores/account";

const {account} = storeToRefs(useAccountStore())
const {setResult} = useResultStore()

const input = ref<number | null>(30)
const loading = ref<boolean>(false)

const withdraw = async () => {
  loading.value = true
  await fetch(`/api/account/${account.value}/withdraw`, {
    method: 'POST',
    body: JSON.stringify({amount: input.value}),
    headers: {'Content-Type': 'application/json'}
  }).then(async r => {
    if (!r.ok) {
      setResult(false, await r.text());
    } else {
      setResult(true, `Successfully withdrew ${input.value} dollars!`);
    }
    loading.value = false
  })
};
</script>

<template>
  <div>
    <div class="mb-3">
      <label for="inputWithdraw" class="form-label">Withdraw</label>
      <input id="inputWithdraw" type="number" class="form-control" v-model="input">
    </div>
    <div>
      <button type="button" class="btn btn-primary" @click.prevent="withdraw()" :disabled="loading">Withdraw</button>
    </div>
  </div>
</template>