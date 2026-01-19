import {
  AeronaveDTO,
  AeronavesPorDecadaDTO,
  AeronavesPorFabricanteDTO,
  NaoVendidasResponse,
} from "./types";

const BASE = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080";

async function http<T>(path: string, init?: RequestInit): Promise<T> {
  const res = await fetch(`${BASE}${path}`, {
    headers: { "Content-Type": "application/json", ...(init?.headers ?? {}) },
    ...init,
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(text || `Erro HTTP ${res.status}`);
  }

  // DELETE 204
  if (res.status === 204) return undefined as unknown as T;
  return (await res.json()) as T;
}

export const api = {
  listAeronaves: () => http<AeronaveDTO[]>("/aeronaves"),

  // opcional: se você tem esse endpoint
  findAeronaves: (termo: string) =>
    http<AeronaveDTO[]>(`/aeronaves/find?termo=${encodeURIComponent(termo)}`),

  createAeronave: (payload: Omit<AeronaveDTO, "id" | "createdAt" | "updateAt">) =>
    http<AeronaveDTO>("/aeronaves", { method: "POST", body: JSON.stringify(payload) }),

  updateAeronave: (id: number, payload: Omit<AeronaveDTO, "id" | "createdAt" | "updateAt">) =>
    http<AeronaveDTO>(`/aeronaves/${id}`, { method: "PUT", body: JSON.stringify(payload) }),

  deleteAeronave: (id: number) =>
    http<void>(`/aeronaves/${id}`, { method: "DELETE" }),

  naoVendidas: () => http<NaoVendidasResponse>("/aeronaves/stats/nao-vendidas"),
  porDecada: () => http<AeronavesPorDecadaDTO[]>("/aeronaves/por-decada"),
  porFabricante: () => http<AeronavesPorFabricanteDTO[]>("/aeronaves/por-fabricante"),
  ultimaSemana: () => http<AeronaveDTO[]>("/aeronaves/ultima-semana"),
};

