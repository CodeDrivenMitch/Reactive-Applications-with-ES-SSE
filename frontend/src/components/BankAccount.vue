<script setup lang="ts">

import {onMounted, ref} from "vue";

const props = defineProps<{ account: string }>()

const balance = ref<number | null>(null)

const inputDeposit = ref<number | null>(20)
const inputWithdraw = ref<number | null>(30)

const depositLoading = ref<boolean>(false)
const withdrawLoading = ref<boolean>(false)
const balanceLoading = ref<boolean>(false)

const error = ref<string | null>(null)
const success = ref<string | null>(null)

const source = new EventSource('/api/balance/' + props.account)
source.onmessage = (e) => {
  balance.value = parseInt(e.data)
}

const setResult = (ok: boolean, text: string) => {
  success.value = null
  error.value = null

  if(ok) {
    success.value = text
  } else {
    error.value = text
  }

  setTimeout(() => {
    success.value = null
    error.value = null
  }, 3000)
}

const deposit = async () => {
  depositLoading.value = true
  await fetch('/api/account/' + props.account + '/deposit', {
    method: 'POST',
    body: JSON.stringify({amount: inputDeposit.value}),
    headers: {'Content-Type': 'application/json'}
  }).then(async r => {
    if (!r.ok) {
       setResult(false, await r.text());
    } else {
      setResult(true, `Successfully deposited ${inputDeposit.value} dollars!`);
    }
    depositLoading.value = false
  })
};

const withdraw = async () => {
  withdrawLoading.value = true
  await fetch('/api/account/' + props.account + '/withdraw', {
    method: 'POST',
    body: JSON.stringify({amount: inputWithdraw.value}),
    headers: {'Content-Type': 'application/json'}
  }).then(async r => {
    if (!r.ok) {
      setResult(false, await r.text());
    } else {
      setResult(true, `Successfully withdrew ${inputWithdraw.value} dollars!`);
    }
    withdrawLoading.value = false
  })
};
</script>

<template>
  <h1>Welcome Valued Customer!</h1>
  <p>
    Account number: {{ props.account }}
  </p>
  <p>
    Balance: <span v-if="balance === null || balanceLoading">Loading...</span><span v-else>${{ balance.toFixed(2) }}</span>
  </p>
  <hr/>
  <div v-if="error">
    <p class="text-danger">
      {{ error }}
    </p>
  </div>
  <div v-if="success">
    <p class="text-success">
      {{ success }}
    </p>
  </div>
  <div class="row mt-5">
    <div class="col-3"></div>
    <div class="col-3">
      <div class="mb-3">
        <label for="inputDeposit" class="form-label">Deposit</label>
        <input id="inputDeposit" type="number" class="form-control" v-model="inputDeposit">
      </div>
      <div>
        <button type="button" class="btn btn-primary" @click.prevent="deposit()" :disabled="depositLoading">Deposit</button>
      </div>
    </div>
    <div class="col-3">
      <div class="mb-3">
        <label for="inputWithdraw" class="form-label">Withdraw</label>
        <input id="inputWithdraw" type="number" class="form-control" v-model="inputWithdraw">
      </div>
      <div>
        <button type="button" class="btn btn-primary" @click.prevent="withdraw()" :disabled="withdrawLoading">Withdraw</button>
      </div>
    </div>
  </div>
</template>