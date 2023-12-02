class APIClient {
  private baseURI = import.meta.env.VITE_API_URL;
  async fetch<T>(
    endpoint: string,
    options?: {
      method?: 'GET' | 'POST' | 'PUT' | 'DELETE';
      body?: Record<string, any>;
      queryParams?: Record<string, any>;
    },
  ): Promise<T> {
    let result;
    try {
      result = await fetch(this.buildUri(endpoint, options?.queryParams), {
        method: options?.method ?? 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        body: options?.body ? JSON.stringify(options.body) : undefined,
      });
    } catch (e: any) {
      console.error(e);
      throw new Error(e);
    }

    if (result.ok) {
      if (result.status === 204) return void 0 as unknown as T;
      return result.json();
    }

    console.error(result);
    throw Error(`API Request Failed: ${result.status}, ${result.statusText}`);
  }

  buildUri(endpoint: string, queryParams?: Record<string, any>): string {
    const sanitizedBase = this.baseURI.endsWith('/')
      ? this.baseURI.slice(0, -1)
      : this.baseURI;
    const sanitizedEndpoint = endpoint.startsWith('/')
      ? endpoint
      : `/${endpoint}`;

    const query = queryParams
      ? `?${Object.entries(queryParams)
          .map(
            ([key, value]) =>
              `${encodeURIComponent(key)}=${encodeURIComponent(value)}`,
          )
          .join('&')}`
      : '';

    return `${sanitizedBase}${sanitizedEndpoint}${query}`;
  }
}
export { APIClient };
