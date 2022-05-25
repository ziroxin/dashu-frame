<template>
  <div class="app-container">
    <el-row>
      <el-col :span="9">
        <!--        资源表格-->
        <div style="margin-bottom: 20px;">
          <el-button @click="toggleTableOprate" v-permission="'abcdefg'">全部{{isExpand?'收起':'展开'}}</el-button>
        </div>
        <div class="grid-content bg-purple">
          <el-table ref="permissionTable" v-loading="listLoading" :default-expand-all="isExpand"
                    style="width: 95%;margin-bottom: 20px;"
                    border :data="tableData" row-key="permissionId"
                    highlight-current-row :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
            <el-table-column label="名称" sortable width="180px">
              <template v-slot="scope">
                {{ scope.row.permissionTitle }}
                <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
                <el-tag v-if="scope.row.permissionType === '1'" disable-transitions type="warning">按钮</el-tag>
                <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
                <el-tag v-if="scope.row.permissionType === '3'" disable-transitions type="danger">其他</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="permissionRouter" label="路由" sortable/>
            <el-table-column label="操作" width="80px" align="center">
              <template v-slot="scope">
                <el-button type="text" size="small" @click.native.prevent="setMyApi(scope.row.permissionId)">
                  设置API
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="15" style="padding-left: 20px;border-left: 1px solid #dedede;">
        <!--        API列表-->
        <div class="grid-content bg-purple-light">
          <div style="margin-bottom: 20px;">
            <el-button type="primary" :disabled="isSaveBtn" @click="savePermissionApi()">保存关联API</el-button>
            <el-button type="danger" @click="scanApi()">扫描API-自动保存到数据库中</el-button>
            <el-button type="info" @click="clearApi()">清除无效的API</el-button>
          </div>
          <div>
            <el-collapse v-model="activeNames">
              <el-collapse-item v-for="group2 in tableData2" :key="group2.apiGroupId"
                                :title="group2.groupName" :name="group2.apiGroupId">
                <el-checkbox-group v-model="selectPermissionApiList" style="line-height: 50px;">
                  <template v-for="cls in group2.apiClass">
                    <el-divider :key="cls.className">{{ cls.className }}</el-divider>
                    <template v-for="api2 in cls.apiList">
                      <el-tooltip :key="'tip'+api2.apiId" placement="left">
                        <div slot="content" :key="'tipcontent'+api2.apiId" style="line-height: 30px;">
                          请求地址：{{ api2.apiRequestUrl }}
                          <br>请求方式：{{ api2.apiRequestMethod }}
                          <br>描述：{{ api2.apiDescription }}
                        </div>
                        <el-checkbox :key="api2.apiId" :label="api2.apiId" border style="margin-left: 0px!important;">
                          {{ api2.apiName }}
                        </el-checkbox>
                      </el-tooltip>
                    </template>
                  </template>
                </el-checkbox-group>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {clearApi, getApiList, getApiListByPermissionId, permissionTreeList, savePermissionApi, scanApi} from '@/api/api'

export default {
  data() {
    return {
      isExpand: true,
      currentPermissionId: '',
      selectPermissionApiList: [],
      activeNames: ['1', '2', '3', '4'],
      tableData: [],
      tableData2: [],
      listLoading: true,
      listLoading2: true,
      isSaveBtn: true
    }
  },
  created() {
    this.getPermissionTreeList()
    this.getApiList();
  },
  methods: {
    toggleTableOprate() {
      if (this.isExpand) {
        this.isExpand = false
      } else {
        this.isExpand = true
      }
      this.toggleRowExpansionAll(this.tableData, this.isExpand)
    },
    toggleRowExpansionAll(data, isExpansion) {
      data.forEach((item) => {
        this.$refs.permissionTable.toggleRowExpansion(item, isExpansion);
        if (item.children !== undefined && item.children !== null) {
          this.toggleRowExpansionAll(item.children, isExpansion);
        }
      });
    },
    // 左侧菜单列表
    async getPermissionTreeList() {
      this.listLoading = true
      const {data} = await permissionTreeList()
      this.tableData = data
      this.listLoading = false
    },
    // 右侧api列表
    async getApiList() {
      this.listLoading2 = true
      const {data} = await getApiList()
      this.activeNames = [];
      for (const group1 of data) {
        this.activeNames.push(group1.apiGroupId);
      }
      this.tableData2 = data
      this.listLoading2 = false
    },
    // 扫描后台api列表
    async scanApi() {
      const {code} = await scanApi()
      if (code === '200') {
        this.$notify({
          title: '扫描成功',
          message: '扫描所有api已完成，并存入数据库中',
          type: 'success'
        });
      }
      // 刷新api列表
      this.getApiList()
    },
    // 清除无效的API
    clearApi() {
      this.$confirm('确定要清除无效的Api吗?', '提醒', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        clearApi().then(response => {
          const {code} = response
          if (code === '200') {
            this.$message({type: 'success', message: '清除无效的API成功！'})
            this.getApiList()
          } else {
            this.$message({type: 'error', message: '操作失败！'})
          }
        })
      })
    },
    // 设置api
    setMyApi(permissionId) {
      getApiListByPermissionId({'permissionId': permissionId})
        .then((response) => {
          const {data} = response
          this.selectPermissionApiList = data || []
          this.currentPermissionId = permissionId
          this.isSaveBtn = false
        })
    },
    // 保存关联API
    savePermissionApi() {
      savePermissionApi({'permissionId': this.currentPermissionId, 'apiIds': this.selectPermissionApiList})
        .then((response) => {
          const {code} = response
          if (code === '200') {
            this.$notify({
              title: '保存成功',
              message: '保存关联API成功！',
              type: 'success'
            });
          }
        })
    }
  }
}
</script>
