export type Fabricante = "EMBRAER" | "BOEING" | "AIRBUS";

export type AeronaveDTO = {
  id?: number;
  nome: string;              // (Modelo na tela)
  fabricante: Fabricante;    // (Marca na tela)
  anoFabricacao: number;     // (Ano na tela)
  descricao: string;
  vendido: boolean;
  createdAt?: string;
  updateAt?: string;
};

export type Page<T> = {
  content: T[];
  number: number;        
  size: number;         
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
};

export type AeronavePorDecadaDTO = {
  anoFabricacao: number;   
  aeronaveDTO: AeronaveDTO[];
};

export type AeronavePorFabricanteDTO = {
  fabricante: string;
  aeronaveDTO: AeronaveDTO[];
};

