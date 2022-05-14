<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      default-expand-all
      style="width: 100%;margin-bottom: 20px;"
      border
      :data="tableData"
      row-key="permissionId"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="permissionTitle" label="名称" sortable width="180" />
      <el-table-column prop="permissionRouter" label="路由" sortable width="180" />
    </el-table>
  </div>
</template>
<script>
import {permissionTreeList} from '@/api/api'

export default {
  data() {
    return {
      tableData: [],
      listLoading: true
    }
  },
  created() {
    this.getPermissionTreeList()
  },
  methods: {
    async getPermissionTreeList() {
      this.listLoading = true
      const {data} = await permissionTreeList()
      console.dir(data)
      this.tableData = data
      // const items = data.items
      // this.list = items.map(v => {
      //   this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
      //   v.originalTitle = v.title //  will be used when user click the cancel botton
      //   return v
      // })
      this.listLoading = false
    }
  }
}
</script>
