<template>
  <div>
    <i v-if="iconName.startsWith('el-icon')" :class="iconName" style="font-size:20px" />
    <svg-icon v-else :name="iconName" width="20px" height="20" />
  </div>
</template>

<script>
export default {
  name: 'EIcon',
  props: {
    iconName: {
      type: String,
      required: true
    }
  }
};
</script>
