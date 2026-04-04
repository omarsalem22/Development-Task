//FROM REST Countries API

export interface CountryApi {
  name: { common: string };
  capital: string[];
  region: string;
  population: number;
  currencies: { [code: string]: { name: string; symbol: string } };
  flags: { png: string };
  cca2: string;
}

// shape of destination saved IN your DB
export interface Destination {
  id: number;
  name: string;
  capital: string;
  region: string;
  population: number;
  currency: string;
  flagUrl: string;
  countryCode: string;
}
