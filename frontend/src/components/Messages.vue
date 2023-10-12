<script setup lang="ts">

import {useResultStore} from "@/stores/result";
import {storeToRefs} from "pinia";

const {error, success} = storeToRefs(useResultStore())

</script>

<template>
  <div class="d-flex justify-content-center mt-2">
    <div v-if="error" class="alert alert-danger">
      {{ error }}
    </div>
    <div v-if="success" class="alert alert-success">
      {{ success }}
    </div>
  </div>
</template>
