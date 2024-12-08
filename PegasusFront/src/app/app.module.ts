import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { CardModule } from "primeng/card";
import { MessageService, SharedModule } from "primeng/api";
import { TableModule } from "primeng/table";
import { ToastModule } from "primeng/toast";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    TableModule,
    CardModule,
    SharedModule,
    ToastModule,
    BrowserAnimationsModule
  ],
  providers: [ MessageService ],
  exports: [],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
