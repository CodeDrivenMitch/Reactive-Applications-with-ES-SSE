<script setup lang="ts">

import DepositForm from "@/components/DepositForm.vue";
import WithdrawForm from "@/components/WithdrawForm.vue";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/stores/account";
import Balance from "@/components/Balance.vue";

const {account} = storeToRefs(useAccountStore())
</script>

<template>
  <h1>Welcome Valued Customer!</h1>
  <p>
    Account number: {{ account }}
  </p>
  <Balance/>
  <hr/>
  <div class="row mt-5">
    <div class="col-3"></div>
    <div class="col-3">
      <DepositForm/>
    </div>
    <div class="col-3">
      <WithdrawForm/>
    </div>
  </div>
</template>