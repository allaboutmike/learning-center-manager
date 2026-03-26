import { useEffect } from "react";
import type { RefObject } from "react";

declare global {
  interface Window {
    OT: any;
  }
}

type Props = {
  whiteboardRef: RefObject<HTMLElement | null>;
};

export function useVonageWhiteboard({ whiteboardRef }: Props) {
  useEffect(() => {
    const loadScript = (src: string, type?: "module") =>
      new Promise<void>((resolve, reject) => {
        const existing = document.querySelector(`script[src="${src}"]`);
        if (existing) return resolve();

        const script = document.createElement("script");
        script.src = src;
        script.async = true;
        if (type === "module") script.type = "module";

        script.onload = () => resolve();
        script.onerror = () => reject();

        document.body.appendChild(script);
      });

    async function init() {
      // load EXACT same scripts from your sample
      await loadScript("https://video.standard.vonage.com/v2/js/opentok.min.js");
      await loadScript(
        "https://cdn.jsdelivr.net/npm/@vonage/white-board@latest/white-board.js/+esm",
        "module"
      );

      const whiteboardEl = whiteboardRef.current as any;

      if (!whiteboardEl || !window.OT) return;

      // ⚠️ replace these later with real backend values
      const applicationId = "YOUR_APPLICATION_ID";
      const sessionId = "YOUR_SESSION_ID";
      const token = "YOUR_TOKEN";

      const session = window.OT.initSession(applicationId, sessionId);

      // 🔥 THIS is what your sample does (missing in your app)
      whiteboardEl.session = session;
      whiteboardEl.token = token;
    }

    init();
  }, [whiteboardRef]);
}