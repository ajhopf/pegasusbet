import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable, tap } from "rxjs";
import { GetJockeysResponse } from "../../models/jockey/GetJockeysResponse";
import { GetHorseResponse } from "../../models/horse/GetHorseResponse";
import { GetJockeyResponse } from "../../models/jockey/GetJockeyResponse";

@Injectable({
  providedIn: 'root'
})
export class JockeyService {
  private API_URL = environment.DATASTORE_API;

  constructor(private httpClient: HttpClient) { }

  fetchJockeys(offset: number, max: number): Observable<GetJockeysResponse> {
    return this.httpClient
      .get<any>(
        `${this.API_URL}/jockeys?offset=${offset}&max=${max}`
      )
  }

  getJockeyById(id: number): Observable<GetJockeyResponse> {
    return this.httpClient
      .get<any>(`${this.API_URL}/jockeys/${id}`)
  }
}
