export type Fabricante = "EMBRAER" | "BOIENG" | "AIRBUS"

export type AeronaveDTO = {
    id?:number;
    name:string;
    fabricante: Fabricante;
    descricao:string;
    vendido:boolean;
    createdAt?:string;
    updatedAt?:string;
};

export type AeronavePorDecadaDTO ={
   decada:number;
   aeronaves:AeronaveDTO[];
};

export type NaoVendidasResponse = {
  naoVendidas: number;
};


