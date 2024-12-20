import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { environment } from "../../../environments/environment";
import { GetHorsesResponse } from "../../models/horse/GetHorsesResponse";
import { Horse } from "../../models/horse/Horse";
import { GetHorseResponse } from "../../models/horse/GetHorseResponse";
import { CookieService } from "ngx-cookie-service";
import { TokenFields } from "../../models/auth/TokenFields";

@Injectable({
  providedIn: 'root'
})

export class HorseService {
  private API_URL = environment.DATASTORE_API;

  private JWT_TOKEN = this.cookie.get(TokenFields.ACCESS_TOKEN);

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${this.JWT_TOKEN}`,
    }),
  };

  constructor(private httpClient: HttpClient, private cookie: CookieService) {}

  fetchHorses(): Observable<GetHorsesResponse> {
    return this.httpClient
      .get<any>(`${this.API_URL}/horses`, this.httpOptions )
  }

  getHorseById(id: number): Observable<GetHorseResponse> {
    return this.httpClient
      .get<any>(`${this.API_URL}/horses/${id}`, this.httpOptions)
  }

  deleteHorse(id: string) {
    return this.httpClient
      .delete<any>(`${this.API_URL}/horses/${id}`,{...this.httpOptions, observe: 'response' })
  }
}
