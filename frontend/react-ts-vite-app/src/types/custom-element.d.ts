import type React from "react";

declare global {
  namespace JSX {
    interface IntrinsicElements {
      "white-board": React.DetailedHTMLProps<
        React.HTMLAttributes<HTMLElement>,
        HTMLElement
      > & {
        ref?: React.Ref<HTMLElement>;
      };
    }
  }
}

export {};