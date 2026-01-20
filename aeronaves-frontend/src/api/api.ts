import type {
  AeronaveDTO,
  Page,
  AeronavePorDecadaDTO,
  AeronavePorFabricanteDTO,
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

  if (res.status === 204) return undefined as unknown as T;
  return (await res.json()) as T;
}

export const api = {
  // Page<AeronaveDTO>
  list: (page = 0, size = 12) =>
    http<Page<AeronaveDTO>>(`/aeronaves?page=${page}&size=${size}`),

  find: (termo: string) =>
    http<AeronaveDTO[]>(`/aeronaves/find?termo=${encodeURIComponent(termo)}`),

  porDecada: () => http<AeronavePorDecadaDTO[]>(`/aeronaves/por-decada`),
  porFabricante: () => http<AeronavePorFabricanteDTO[]>(`/aeronaves/por-fabricante`),
  ultimaSemana: () => http<AeronaveDTO[]>(`/aeronaves/ultima-semana`),

  create: (payload: Omit<AeronaveDTO, "id">) =>
    http<AeronaveDTO>(`/aeronaves`, { method: "POST", body: JSON.stringify(payload) }),

  update: (id: number, payload: Omit<AeronaveDTO, "id">) =>
    http<AeronaveDTO>(`/aeronaves/${id}`, { method: "PUT", body: JSON.stringify(payload) }),

  patch: (id: number, payload: Partial<AeronaveDTO>) =>
    http<AeronaveDTO>(`/aeronaves/${id}`, { method: "PATCH", body: JSON.stringify(payload) }),

  remove: (id: number) =>
    http<void>(`/aeronaves/${id}`, { method: "DELETE" }),
};
