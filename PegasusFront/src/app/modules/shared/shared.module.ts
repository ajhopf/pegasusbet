import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CardModule } from "primeng/card";
import { SharedModule as SharedModuleNg } from "primeng/api";
import { TableModule } from "primeng/table";

import { TableComponent } from './components/table/table.component';
import { HeaderMenuBarComponent } from './components/header-menu-bar/header-menu-bar.component';
import { MenubarModule } from "primeng/menubar";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { FormsModule } from "@angular/forms";


@NgModule({
  declarations: [
    TableComponent,
    HeaderMenuBarComponent
  ],
  imports: [
    CommonModule,
    CardModule,
    SharedModuleNg,
    TableModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    FormsModule
  ],
  exports: [
    TableComponent,
    HeaderMenuBarComponent
  ]
})
export class SharedModule { }
