import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../../environments/environment";
import { GetHorsesResponse } from "../../models/horse/GetHorsesResponse";

@Injectable({
  providedIn: 'root'
})

export class HorseService {
  private API_URL = environment.DATASTORE_API;

  constructor(private httpClient: HttpClient) { }

  fetchHorses(): Observable<GetHorsesResponse> {
    return this.httpClient
      .get<any>(`${this.API_URL}/horses` )
  }

  deleteHorse(id: string) {
    return this.httpClient
      .delete<any>(`${this.API_URL}/horses/${id}`,{ observe: 'response' })
  }
}
