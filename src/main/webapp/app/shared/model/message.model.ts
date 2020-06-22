export interface IMessage {
  id?: number;
  subject?: string;
  sendafter?: number;
}

export const defaultValue: Readonly<IMessage> = {};
